package ddog.vet.application.mapper;

import ddog.domain.vet.Vet;
import ddog.vet.presentation.account.dto.ModifyInfoReq;
import ddog.vet.presentation.account.dto.ProfileInfo;
import ddog.vet.presentation.account.dto.SignUpReq;

import java.time.LocalTime;

public class VetMapper {

    public static Vet create(Long accountId, SignUpReq request) {
        return Vet.builder()
                .accountId(accountId)
                .daengleMeter(0)
                .name(request.getName())
                .address(request.getAddress())
                .detailAddress(request.getDetailAddress())
                .phoneNumber(request.getPhoneNumber())
                .licenses(request.getLicenses())
                .email(request.getEmail())
                .startTime(LocalTime.of(0, 0))
                .endTime(LocalTime.of(23, 59))
                .build();
    }

    public static ProfileInfo.ModifyPage toModifyPage(Vet vet) {
        return ProfileInfo.ModifyPage.builder()
                .image(vet.getImageURL())
                .name(vet.getName())
                .startTime(vet.getStartTime())
                .endTime(vet.getEndTime())
                .closedDays(vet.getClosedDays())
                .phoneNumber(vet.getPhoneNumber())
                .address(vet.getAddress())
                .detailAddress(vet.getDetailAddress())
                .introduction(vet.getIntroduction())
                .build();
    }

    public static Vet withUpdate(Vet vet, ModifyInfoReq request) {
        return Vet.builder()
                .vetId(vet.getVetId())
                .accountId(vet.getAccountId())
                .email(vet.getEmail())
                .daengleMeter(vet.getDaengleMeter())
                .name(vet.getName())
                .imageURL(request.getImage())
                .address(vet.getAddress())
                .detailAddress(vet.getDetailAddress())
                .phoneNumber(request.getPhoneNumber())
                .introduction(request.getIntroduction())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .closedDays(request.getClosedDays())
                .licenses(vet.getLicenses())
                .build();
    }
}
