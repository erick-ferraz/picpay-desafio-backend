package br.com.picpay.service;

import br.com.picpay.entity.Wallet;
import br.com.picpay.entity.dto.UpdateWalletParamsDto;
import br.com.picpay.entity.dto.WalletRequestDto;
import br.com.picpay.entity.dto.WalletResponseDto;
import br.com.picpay.exception.AlreadyRegisteredDataException;
import br.com.picpay.exception.DefaultException;
import br.com.picpay.exception.InputValidationException;
import br.com.picpay.repository.WalletRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class WalletService {

    private final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public WalletResponseDto createWallet(WalletRequestDto dto) {
        dto.validateFields();

        Wallet wallet = repository.getByEmailOrCpfCnpj(dto.email(), dto.cpfCnpj());
        if(Objects.nonNull(wallet)) {
            throw new AlreadyRegisteredDataException("Email or cpfCnpj already exists");
        }

        Wallet entity = dto.toEntity();
        repository.persist(entity);
        return new WalletResponseDto(entity);
    }

    public Wallet getById(Long id) {
        return repository.getById(id);
    }

    public List<WalletResponseDto> getAll() {
        return repository.listAll().stream()
            .map(WalletResponseDto::new)
            .toList();
    }

    @Transactional
    public void updateById(Long id, UpdateWalletParamsDto dto) {
        dto.validateFields();

        if(Objects.isNull(id)) {
            throw new InputValidationException("Id is required", Collections.emptyMap());
        }

        Wallet wallet = getById(id);
        wallet.setName(dto.name() == null ? wallet.getName() : dto.name());
        wallet.setEmail(dto.email() == null ? wallet.getEmail() : dto.email());
        wallet.setPassword(dto.password() == null ? wallet.getPassword() : dto.password());
        repository.persist(wallet);
    }

    @Transactional
    public void deleteById(Long id) {
        if(Objects.isNull(id)) {
            throw new InputValidationException("Id is required", Collections.emptyMap());
        }

        Wallet wallet = getById(id);
        if(wallet.hasBalance()) {
            throw new DefaultException("You must have no balance to delete wallet");
        }
        repository.deleteById(id);
    }
}
