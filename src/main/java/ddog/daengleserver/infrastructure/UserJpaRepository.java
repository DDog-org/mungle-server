package ddog.daengleserver.infrastructure;

import ddog.daengleserver.infrastructure.jpa.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
}
