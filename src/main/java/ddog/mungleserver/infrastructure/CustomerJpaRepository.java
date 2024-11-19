package ddog.mungleserver.infrastructure;

import ddog.mungleserver.global.auth.config.enums.Provider;
import ddog.mungleserver.infrastructure.jpa.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, Long> {

    boolean existsByEmailAndProvider(String email, Provider provider);

    Optional<CustomerJpaEntity> findByEmailAndProvider(String email, Provider provider);

}
