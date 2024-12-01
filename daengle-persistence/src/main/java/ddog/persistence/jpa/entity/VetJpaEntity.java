package ddog.persistence.jpa.entity;

import ddog.domain.account.Status;
import ddog.domain.vet.Day;
import ddog.domain.vet.Vet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Vets")
public class VetJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vetId;
    private Long accountId;
    private Status status;
    private String email;
    private int daengleMeter;
    private String vetName;
    private String vetImage;
    private String address;
    private String detailAddress;
    private String phoneNumber;
    private String vetIntroduction;
    private LocalTime startTime;
    private LocalTime endTime;

    @ElementCollection // 휴무일 리스트
    @CollectionTable(name = "vet_closed_days", joinColumns = @JoinColumn(name = "vet_id"))
    @Column(name = "day")
    private List<Day> closedDays;

    @ElementCollection // 자격증 URL 리스트
    @CollectionTable(name = "vet_licenses", joinColumns = @JoinColumn(name = "vet_id"))
    @Column(name = "license_url")
    private List<String> licenses;

    public static VetJpaEntity from(Vet vet) {
        return VetJpaEntity.builder()
                .vetId(vet.getVetId())
                .accountId(vet.getAccountId())
                .status(vet.getStatus())
                .email(vet.getEmail())
                .daengleMeter(vet.getDaengleMeter())
                .vetName(vet.getVetName())
                .vetImage(vet.getVetImage())
                .address(vet.getAddress())
                .detailAddress(vet.getDetailAddress())
                .phoneNumber(vet.getPhoneNumber())
                .vetIntroduction(vet.getVetIntroduction())
                .startTime(vet.getStartTime())
                .endTime(vet.getEndTime())
                .closedDays(vet.getClosedDays())
                .licenses(vet.getLicenses())
                .build();
    }

    public Vet toModel() {
        return Vet.builder()
                .vetId(vetId)
                .accountId(accountId)
                .status(status)
                .email(email)
                .daengleMeter(daengleMeter)
                .vetName(vetName)
                .vetImage(vetImage)
                .address(address)
                .detailAddress(detailAddress)
                .phoneNumber(phoneNumber)
                .vetIntroduction(vetIntroduction)
                .startTime(startTime)
                .endTime(endTime)
                .closedDays(closedDays)
                .licenses(licenses)
                .build();
    }
}
