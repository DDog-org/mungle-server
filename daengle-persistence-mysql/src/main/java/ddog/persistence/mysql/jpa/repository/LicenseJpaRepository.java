package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.LicenseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseJpaRepository extends JpaRepository<LicenseJpaEntity, Long> {

    LicenseJpaEntity save(LicenseJpaEntity license);

}
