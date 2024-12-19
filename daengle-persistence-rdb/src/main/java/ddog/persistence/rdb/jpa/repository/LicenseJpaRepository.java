package ddog.persistence.rdb.jpa.repository;

import ddog.persistence.rdb.jpa.entity.LicenseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseJpaRepository extends JpaRepository<LicenseJpaEntity, Long> {

    LicenseJpaEntity save(LicenseJpaEntity license);

}
