package ddog.groomer.application.mapper;

import ddog.domain.groomer.Groomer;
import ddog.groomer.presentation.account.dto.UpdateInfoReq;
import ddog.groomer.presentation.account.dto.ProfileInfo;
import ddog.groomer.presentation.account.dto.SignUpReq;

public class GroomerMapper {

    public static Groomer create(Long accountId, SignUpReq request) {
        return Groomer.builder()
                .accountId(accountId)
                .daengleMeter(0)
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .address(request.getAddress())
                .detailAddress(request.getDetailAddress())
                .shopName(request.getShopName())
                .businessLicenses(request.getBusinessLicenses())
                .licenses(request.getLicenses())
                .build();
    }

    public static ProfileInfo.UpdatePage toModifyPage(Groomer groomer) {
        return ProfileInfo.UpdatePage.builder()
                .image(groomer.getImageUrl())
                .name(groomer.getName())
                .phoneNumber(groomer.getPhoneNumber())
                .email(groomer.getEmail())
                .introduction(groomer.getIntroduction())
                .businessLicences(groomer.getBusinessLicenses())
                .licenses(groomer.getLicenses())
                .build();
    }

    public static Groomer withUpdate(Groomer groomer, UpdateInfoReq request) {
        return Groomer.builder()
                .groomerId(groomer.getGroomerId())
                .accountId(groomer.getAccountId())
                .daengleMeter(groomer.getDaengleMeter())
                .name(groomer.getName())
                .phoneNumber(groomer.getPhoneNumber())
                .imageUrl(request.getImage())
                .email(groomer.getEmail())
                .address(groomer.getAddress())
                .shopName(groomer.getShopName())
                .introduction(request.getIntroduction())
                .businessLicenses(groomer.getBusinessLicenses())
                .licenses(groomer.getLicenses())
                .build();
    }
}
