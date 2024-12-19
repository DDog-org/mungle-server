package ddog.persistence.rdb.jpa.repository;

import ddog.persistence.rdb.jpa.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    boolean existsByNickname(String nickname);

    Optional<UserJpaEntity> findByAccountId(Long accountId);

    void deleteByAccountId(Long accountId);
}
