package br.com.picpay.service;

import br.com.picpay.entity.Wallet;
import br.com.picpay.entity.dto.WalletRequestDto;
import br.com.picpay.repository.WalletRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class WalletService {

    private WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Wallet createWallet(WalletRequestDto dto) {
        dto.validateFields();

        var entity = dto.toEntity();
        repository.persist(entity);
        return entity;
    }
}
