package ddog.persistence;

import ddog.daengleserver.infrastructure.po.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    boolean existsByNickname(String nickname);

    Optional<UserJpaEntity> findByAccountId(Long accountId);
}
