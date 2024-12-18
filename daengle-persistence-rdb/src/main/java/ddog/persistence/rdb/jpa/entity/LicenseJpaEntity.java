package ddog.persistence.rdb.jpa.entity;

import ddog.domain.groomer.License;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Licenses")
public class LicenseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long licenseId;

    @Column(name = "account_id")
    private Long accountId;
    private String imageUrl;
    private String name;
    private LocalDate acquisitionDate;

    public static LicenseJpaEntity from(License license) {
        return LicenseJpaEntity.builder()
                .licenseId(license.getLicenseId())
                .accountId(license.getAccountId())
                .imageUrl(license.getImageUrl())
                .name(license.getName())
                .acquisitionDate(license.getAcquisitionDate())
                .build();
    }

    public License toModel() {
        return License.builder()
                .licenseId(licenseId)
                .accountId(accountId)
                .imageUrl(imageUrl)
                .name(name)
                .acquisitionDate(acquisitionDate)
                .build();
    }
}
