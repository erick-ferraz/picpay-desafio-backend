package br.com.picpay.repository;

import br.com.picpay.entity.Transference;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransferenceRepository implements PanacheRepository<Transference> {

}
