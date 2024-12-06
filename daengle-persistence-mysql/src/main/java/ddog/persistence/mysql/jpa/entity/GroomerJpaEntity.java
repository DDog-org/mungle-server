package ddog.persistence.mysql.jpa.entity;

import ddog.domain.groomer.Groomer;
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
    private String groomerName;
    private String phoneNumber;
    private String groomerImage;
    private String email;
    private String address;
    private String detailAddress;
    private String shopName;
    private String groomerIntroduction;

    @ElementCollection // 사업자 등록증 URL 리스트
    @CollectionTable(name = "groomer_business_licenses", joinColumns = @JoinColumn(name = "groomer_id"))
    @Column(name = "business_license_url")
    private List<String> businessLicenses;
    @ElementCollection // 자격증 URL 리스트
    @CollectionTable(name = "groomer_licenses", joinColumns = @JoinColumn(name = "groomer_id"))
    @Column(name = "license_url")

    private List<String> licenses;

    public static GroomerJpaEntity from(Groomer groomer) {
        return GroomerJpaEntity.builder()
                .groomerId(groomer.getGroomerId())
                .accountId(groomer.getAccountId())
                .daengleMeter(groomer.getDaengleMeter())
                .groomerName(groomer.getGroomerName())
                .phoneNumber(groomer.getPhoneNumber())
                .groomerImage(groomer.getGroomerImage())
                .email(groomer.getEmail())
                .address(groomer.getAddress())
                .detailAddress(groomer.getDetailAddress())
                .shopName(groomer.getShopName())
                .groomerIntroduction(groomer.getGroomerIntroduction())
                .businessLicenses(groomer.getBusinessLicenses())
                .licenses(groomer.getLicenses())
                .build();
    }

    public Groomer toModel() {
        return Groomer.builder()
                .groomerId(groomerId)
                .accountId(accountId)
                .daengleMeter(daengleMeter)
                .groomerName(groomerName)
                .phoneNumber(phoneNumber)
                .groomerImage(groomerImage)
                .email(email)
                .address(address)
                .detailAddress(detailAddress)
                .shopName(shopName)
                .groomerIntroduction(groomerIntroduction)
                .businessLicenses(businessLicenses)
                .licenses(licenses)
                .build();
    }
}
