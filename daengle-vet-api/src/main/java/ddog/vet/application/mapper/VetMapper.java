package ddog.vet.application.mapper;

import ddog.domain.vet.Vet;
import ddog.vet.presentation.account.dto.UpdateInfo;
import ddog.vet.presentation.account.dto.ProfileInfo;
import ddog.vet.presentation.account.dto.SignUpReq;

import java.time.LocalTime;
import java.util.ArrayList;

public class VetMapper {

    public static Vet create(Long accountId, SignUpReq request) {
        return Vet.builder()
                .accountId(accountId)
                .email(request.getEmail())
                .daengleMeter(50)
                .name(request.getName())
                .imageUrl("")
                .imageUrlList(null)
                .address(request.getAddress())
                .detailAddress(request.getDetailAddress())
                .phoneNumber(request.getPhoneNumber())
                .introduction(null)
                .startTime(LocalTime.of(0, 0))
                .endTime(LocalTime.of(23, 59))
                .closedDays(null)
                .licenses(request.getLicenses())
                .badges(null)
                .keywords(new ArrayList<>())
                .build();
    }

    public static ProfileInfo mapToProfileInfo(Vet vet) {
        return ProfileInfo.builder()
                .imageUrls(vet.getImageUrlList())
                .name(vet.getName())
                .badges(vet.getBadges())
                .closedDays(vet.getClosedDays())
                .startTime(vet.getStartTime())
                .endTime(vet.getEndTime())
                .phoneNumber(vet.getPhoneNumber())
                .address(vet.getAddress())
                .detailAddress(vet.getDetailAddress())
                .introduction(vet.getIntroduction())
                .daengleMeter(vet.getDaengleMeter())
                .build();
    }

    public static ProfileInfo.UpdatePage mapToUpdatePage(Vet vet) {
        return ProfileInfo.UpdatePage.builder()
                .imageUrls(vet.getImageUrlList())
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

    public static Vet updateWithUpdateInfo(Vet vet, UpdateInfo request, String imageUrl) {
        return Vet.builder()
                .vetId(vet.getVetId())
                .accountId(vet.getAccountId())
                .email(vet.getEmail())
                .daengleMeter(vet.getDaengleMeter())
                .name(vet.getName())
                .imageUrl(imageUrl)
                .imageUrlList(request.getImageUrls())
                .address(vet.getAddress())
                .detailAddress(vet.getDetailAddress())
                .phoneNumber(request.getPhoneNumber())
                .introduction(request.getIntroduction())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .closedDays(request.getClosedDays())
                .licenses(vet.getLicenses())
                .badges(vet.getBadges())
                .keywords(vet.getKeywords())
                .build();
    }
}
