package ddog.persistence.mysql.jpa.entity;

import ddog.domain.groomer.Groomer;
import ddog.domain.groomer.enums.GroomingBadge;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Groomers")
public class GroomerJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groomerId;
    private Long accountId;
    private int daengleMeter;
    private String instagramId;
    private String name;
    private String phoneNumber;
    private String imageUrl;
    private String email;
    private String address;
    private String detailAddress;
    private Long shopId;
    private String shopName;
    private String introduction;

    @ElementCollection // 사업자 등록증 URL 리스트
    @CollectionTable(name = "groomer_business_licenses", joinColumns = @JoinColumn(name = "groomer_id"))
    @Column(name = "business_license_url")
    private List<String> businessLicenses;

    @OneToMany
    @JoinColumn(name = "account_id", referencedColumnName = "accountId")
    private List<LicenseJpaEntity> licenses;

    @ElementCollection // 뱃지 리스트
    @CollectionTable(name = "grooming_badges", joinColumns = @JoinColumn(name = "groomer_id"))
    @Column(name = "grooming_badge")
    @Enumerated(EnumType.STRING)
    private List<GroomingBadge> badges;

    @OneToMany
    @JoinColumn(name = "account_id", referencedColumnName = "accountId")
    private List<GroomerKeywordJpaEntity> keywords;

    public static GroomerJpaEntity from(Groomer groomer) {
        return GroomerJpaEntity.builder()
                .groomerId(groomer.getGroomerId())
                .accountId(groomer.getAccountId())
                .daengleMeter(groomer.getDaengleMeter())
                .instagramId(groomer.getInstagramId())
                .name(groomer.getName())
                .phoneNumber(groomer.getPhoneNumber())
                .imageUrl(groomer.getImageUrl())
                .email(groomer.getEmail())
                .address(groomer.getAddress())
                .detailAddress(groomer.getDetailAddress())
                .shopId(groomer.getShopId())
                .shopName(groomer.getShopName())
                .introduction(groomer.getIntroduction())
                .businessLicenses(groomer.getBusinessLicenses())
                .licenses(groomer.getLicenses().stream()
                        .map(LicenseJpaEntity::from)
                        .toList())
                .badges(groomer.getBadges())
                .keywords(groomer.getKeywords().stream()
                        .map(GroomerKeywordJpaEntity::from)
                        .toList())
                .build();
    }

    public Groomer toModel() {
        return Groomer.builder()
                .groomerId(groomerId)
                .accountId(accountId)
                .daengleMeter(daengleMeter)
                .instagramId(instagramId)
                .name(name)
                .phoneNumber(phoneNumber)
                .imageUrl(imageUrl)
                .email(email)
                .address(address)
                .detailAddress(detailAddress)
                .shopId(shopId)
                .shopName(shopName)
                .introduction(introduction)
                .businessLicenses(businessLicenses)
                .licenses(licenses.stream()
                        .map(LicenseJpaEntity::toModel)
                        .toList())
                .badges(badges)
                .keywords(keywords.stream()
                        .map(GroomerKeywordJpaEntity::toModel)
                        .toList())
                .build();
    }
}
