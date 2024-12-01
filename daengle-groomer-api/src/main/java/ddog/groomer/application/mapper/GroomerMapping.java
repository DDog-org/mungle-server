package ddog.groomer.application.mapper;

import ddog.domain.groomer.Groomer;
import ddog.groomer.presentation.account.dto.ProfileInfo;
import ddog.groomer.presentation.account.dto.SignUpReq;

public class GroomerMapping {

    public static Groomer create(Long accountId, SignUpReq request) {
        return Groomer.builder()
                .accountId(accountId)
                .daengleMeter(0)
                .groomerName(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .address(request.getAddress())
                .shopName(request.getShopName())
                .businessLicenses(request.getBusinessLicenses())
                .licenses(request.getLicenses())
                .build();
    }

    public static ProfileInfo.ModifyPage toModifyPage(Groomer groomer) {
        return ProfileInfo.ModifyPage.builder()
                .image(groomer.getGroomerImage())
                .name(groomer.getGroomerName())
                .phoneNumber(groomer.getPhoneNumber())
                .email(groomer.getEmail())
                .introduction(groomer.getGroomerIntroduction())
                .businessLicences(groomer.getBusinessLicenses())
                .licenses(groomer.getLicenses())
                .build();
    }
}
