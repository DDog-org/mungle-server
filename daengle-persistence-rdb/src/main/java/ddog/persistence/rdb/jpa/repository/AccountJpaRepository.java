package ddog.persistence.rdb.jpa.repository;

import ddog.domain.account.Provider;
import ddog.domain.account.Role;
import ddog.persistence.rdb.jpa.entity.AccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountJpaRepository extends JpaRepository<AccountJpaEntity, Long> {

    boolean existsByEmailAndRole(String email, Role role);

    Optional<AccountJpaEntity> findByEmailAndProvider(String email, Provider provider);

    Optional<AccountJpaEntity> findByEmailAndRole(String email, Role role);
}
