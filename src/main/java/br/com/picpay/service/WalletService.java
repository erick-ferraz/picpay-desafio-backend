package br.com.picpay.service;

import br.com.picpay.entity.Wallet;
import br.com.picpay.entity.dto.WalletRequestDto;
import br.com.picpay.exception.AlreadyRegisteredDataException;
import br.com.picpay.repository.WalletRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Objects;

@ApplicationScoped
public class WalletService {

    private final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Wallet createWallet(WalletRequestDto dto) {
        dto.validateFields();

        Wallet wallet = repository.getByEmailOrCpfCnpj(dto.email(), dto.cpfCnpj());
        if(Objects.nonNull(wallet)) {
            throw new AlreadyRegisteredDataException("Email or cpfCnpj already exists");
        }

        var entity = dto.toEntity();
        repository.persist(entity);
        return entity;
    }
}
