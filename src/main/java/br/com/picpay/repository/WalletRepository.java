package br.com.picpay.repository;

import br.com.picpay.entity.Wallet;
import br.com.picpay.exception.NonExistentWalletException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WalletRepository implements PanacheRepository<Wallet> {

    public Wallet getByEmailOrCpfCnpj(String email, String cpfCnpj) {
        return find("email = ?1 OR cpfCnpj = ?2", email, cpfCnpj)
            .firstResult();
    }

    public Wallet getByEmail(String email) {
        return find("email = ?1", email).firstResult();
    }

    public Wallet getById(Long id) {
        return findByIdOptional(id)
            .orElseThrow(() -> new NonExistentWalletException("Provided wallet could not be found"));
    }
}
