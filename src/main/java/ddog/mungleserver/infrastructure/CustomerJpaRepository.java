package ddog.mungleserver.infrastructure;

import ddog.mungleserver.global.auth.config.enums.Provider;
import ddog.mungleserver.infrastructure.jpa.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, Long> {

    @Query("select case when count(a) = 0 then true else false end from Customer a where a.email = :email and a.provider = :provider")
    boolean notExistsAccountByEmailAndProvider(String email, Provider provider);

    boolean existsByEmailAndProvider(String email, Provider provider);

    Optional<CustomerJpaEntity> findByEmailAndProvider(String email, Provider provider);

}
