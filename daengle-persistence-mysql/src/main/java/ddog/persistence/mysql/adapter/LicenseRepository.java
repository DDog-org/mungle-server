package ddog.persistence.mysql.adapter;

import ddog.domain.groomer.License;
import ddog.domain.groomer.port.LicensePersist;
import ddog.persistence.mysql.jpa.entity.LicenseJpaEntity;
import ddog.persistence.mysql.jpa.repository.LicenseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LicenseRepository implements LicensePersist {

    private final LicenseJpaRepository licenseJpaRepository;

    @Override
    public License save(License license) {
        return licenseJpaRepository.save(LicenseJpaEntity.from(license))
                .toModel();
    }
}
