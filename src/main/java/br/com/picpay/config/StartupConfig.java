package br.com.picpay.config;

import br.com.picpay.entity.enums.Type;
import br.com.picpay.repository.OwnerTypeRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class StartupConfig {

    private static final Logger LOGGER = Logger.getLogger("ListenerBean");

    private final OwnerTypeRepository ownerTypeRepository;

    public StartupConfig(OwnerTypeRepository ownerTypeRepository) {
        this.ownerTypeRepository = ownerTypeRepository;
    }

    @Transactional
    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...");

        List<Type> enumTypes = Arrays.asList(Type.values());
        if(ownerTypeRepository.listAll().isEmpty()) {
            enumTypes.forEach(type -> ownerTypeRepository.persist(type.toOwnerType()));
        }
    }
}
