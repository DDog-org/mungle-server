package ddog.daengleserver.infrastructure;

import ddog.daengleserver.infrastructure.po.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
}
