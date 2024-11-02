package br.com.picpay.repository;

import br.com.picpay.entity.Wallet;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WalletRepository implements PanacheRepository<Wallet> {

    public Wallet getByEmailOrCpfCnpj(String email, String cpfCnpj) {
        return find("email = ?1 OR cpfCnpj = ?2", email, cpfCnpj)
            .firstResult();
    }
}
