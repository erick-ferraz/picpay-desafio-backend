package br.com.picpay.repository;

import br.com.picpay.entity.OwnerType;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OwnerTypeRepository implements PanacheRepository<OwnerType> {

}
