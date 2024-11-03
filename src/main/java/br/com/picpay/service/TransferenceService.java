package br.com.picpay.service;

import br.com.picpay.client.AuthorizerRestClient;
import br.com.picpay.entity.Transference;
import br.com.picpay.entity.Wallet;
import br.com.picpay.entity.dto.TransferenceRequestDto;
import br.com.picpay.exception.*;
import br.com.picpay.repository.TransferenceRepository;
import br.com.picpay.repository.WalletRepository;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@ApplicationScoped
public class TransferenceService {

    private final TransferenceRepository transferenceRepository;
    private final WalletRepository walletRepository;

    @RestClient
    @Inject
    AuthorizerRestClient client;

    public TransferenceService(TransferenceRepository transferenceRepository,
                               WalletRepository walletRepository) {
        this.transferenceRepository = transferenceRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Object transfer(TransferenceRequestDto dto) {
        dto.validateFields();

        Wallet payer = walletRepository.getById(dto.payer());
        Wallet payee = walletRepository.getById(dto.payee());

        validateBusinessLogic(payer, payee, dto.amount());

        payer.debit(dto.amount());
        payee.credit(dto.amount());
        walletRepository.persist(payer);
        walletRepository.persist(payee);

        consultAuthorizer();

        Transference transference = dto.toEntity();
        transferenceRepository.persist(transference);

        //Notificar o recebedor enviando um evento de transferencia para uma fila no rabbit

        return transference;
    }

    private void validateBusinessLogic(Wallet payer,
                                       Wallet payee,
                                       BigDecimal amount) {
        if(!payer.isUser()) {
            throw new OwnerTypeException("Only owners with USER type can make transfers");
        }

        if(payer == payee) {
            throw new SelfTransferException("Transfers to same account are not allowed");
        }

        if(amount.compareTo(payer.getBalance()) > 0) {
            throw new InsufficientBalanceException("Wallet does not have enough balance");
        }
    }

    private void consultAuthorizer() {
        try(Response response = client.consultAuthorizer()) {
            if(response.getStatus() != HttpResponseStatus.OK.code()) {
                throw new UnauthorizedTransferException("Not authorized to make transfers");
            }
        }
    }
}
