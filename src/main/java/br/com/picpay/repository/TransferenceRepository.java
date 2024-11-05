package br.com.picpay.repository;

import br.com.picpay.entity.Transference;
import br.com.picpay.exception.NonExistentTransferenceException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransferenceRepository implements PanacheRepository<Transference> {

    public Transference getById(Long id) {
        return findByIdOptional(id)
            .orElseThrow(() -> new NonExistentTransferenceException("Provided transference ID could not be found"));
    }
}
