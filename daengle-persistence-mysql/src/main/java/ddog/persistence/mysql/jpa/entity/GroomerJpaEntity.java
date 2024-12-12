package ddog.persistence.mysql.jpa.entity;

import ddog.domain.groomer.Groomer;
import ddog.domain.groomer.enums.GroomingKeyword;
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
    private String name;
    private String phoneNumber;
    private String imageUrl;
    private String email;
    private String address;
    private String detailAddress;
    private String shopName;
    private String introduction;

    @ElementCollection // 사업자 등록증 URL 리스트
    @CollectionTable(name = "groomer_business_licenses", joinColumns = @JoinColumn(name = "groomer_id"))
    @Column(name = "business_license_url")
    private List<String> businessLicenses;

    @ElementCollection // 자격증 URL 리스트
    @CollectionTable(name = "groomer_licenses", joinColumns = @JoinColumn(name = "groomer_id"))
    @Column(name = "license_url")
    private List<String> licenses;

    @ElementCollection // 해시태그 리스트
    @CollectionTable(name = "grooming_keywords", joinColumns = @JoinColumn(name = "groomer_id"))
    @Column(name = "grooming_keyword")
    private List<GroomingKeyword> keywords;

    public static GroomerJpaEntity from(Groomer groomer) {
        return GroomerJpaEntity.builder()
                .groomerId(groomer.getGroomerId())
                .accountId(groomer.getAccountId())
                .daengleMeter(groomer.getDaengleMeter())
                .name(groomer.getName())
                .phoneNumber(groomer.getPhoneNumber())
                .imageUrl(groomer.getImageUrl())
                .email(groomer.getEmail())
                .address(groomer.getAddress())
                .detailAddress(groomer.getDetailAddress())
                .shopName(groomer.getShopName())
                .introduction(groomer.getIntroduction())
                .businessLicenses(groomer.getBusinessLicenses())
                .licenses(groomer.getLicenses())
                .keywords(groomer.getKeywords())
                .build();
    }

    public Groomer toModel() {
        return Groomer.builder()
                .groomerId(groomerId)
                .accountId(accountId)
                .daengleMeter(daengleMeter)
                .name(name)
                .phoneNumber(phoneNumber)
                .imageUrl(imageUrl)
                .email(email)
                .address(address)
                .detailAddress(detailAddress)
                .shopName(shopName)
                .introduction(introduction)
                .businessLicenses(businessLicenses)
                .licenses(licenses)
                .keywords(keywords)
                .build();
    }
}
