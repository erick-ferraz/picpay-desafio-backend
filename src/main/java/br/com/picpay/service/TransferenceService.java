package br.com.picpay.service;

import br.com.picpay.entity.Transference;
import br.com.picpay.entity.Wallet;
import br.com.picpay.entity.dto.NotificationEventDto;
import br.com.picpay.entity.dto.TransferenceNotificationDto;
import br.com.picpay.entity.dto.TransferenceRequestDto;
import br.com.picpay.exception.*;
import br.com.picpay.repository.TransferenceRepository;
import br.com.picpay.repository.WalletRepository;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class TransferenceService {

    private final TransferenceRepository transferenceRepository;
    private final WalletRepository walletRepository;
    private final NotificationRabbitmqProducer notificationProducer;
    private final AuthorizerClient authorizerClient;

    public TransferenceService(TransferenceRepository transferenceRepository,
                               WalletRepository walletRepository,
                               NotificationRabbitmqProducer notificationProducer,
                               AuthorizerClient authorizerClient) {

        this.transferenceRepository = transferenceRepository;
        this.walletRepository = walletRepository;
        this.notificationProducer = notificationProducer;
        this.authorizerClient = authorizerClient;
    }

    @Transactional
    public Transference transfer(TransferenceRequestDto dto) {
        dto.validateFields();

        Wallet payer = walletRepository.getById(dto.payer());
        Wallet payee = walletRepository.getById(dto.payee());

        validateTransferBusinessLogic(payer, dto.amount());

        payer.debit(dto.amount());
        payee.credit(dto.amount());
        walletRepository.persist(payer);
        walletRepository.persist(payee);

        consultAuthorizer();

        Transference transference = dto.toEntity();
        transferenceRepository.persist(transference);

        NotificationEventDto notification = new NotificationEventDto(
            "You received a transference!",
            new TransferenceNotificationDto(
                payee.getEmail(),
                payer.getEmail(),
                transference.getAmount(),
                transference.getDateTime()
            )
        );

        notificationProducer.sendNotificationToQueue(notification);

        return transference;
    }

    private void validateTransferBusinessLogic(Wallet payer,
                                               BigDecimal amount) {
        if(!payer.isUser()) {
            throw new OwnerTypeException("Only owners with USER type can make transfers");
        }

        if(amount.compareTo(payer.getBalance()) > 0) {
            throw new InsufficientBalanceException("Wallet does not have enough balance");
        }
    }

    private void consultAuthorizer() {
        try(Response response = authorizerClient.requestClient()) {
            if(response.getStatus() != HttpResponseStatus.OK.code()) {
                throw new UnauthorizedTransferException("Not authorized to make transfers");
            }
        }
    }

    public Transference getById(Long id) {
        if(Objects.isNull(id)) {
            throw new InputValidationException("Id is required", Collections.emptyMap());
        }

        return transferenceRepository.getById(id);
    }

    public List<Transference> listAllTransfersSentByWalletId(Long walletId) {
        return transferenceRepository.listAll().stream()
            .filter(transference -> transference.getPayer().equals(walletId))
            .toList();
    }

    public List<Transference> listAllTransfersReceivedByWalletId(Long walletId) {
        return transferenceRepository.listAll().stream()
            .filter(transference -> transference.getPayee().equals(walletId))
            .toList();
    }
}
