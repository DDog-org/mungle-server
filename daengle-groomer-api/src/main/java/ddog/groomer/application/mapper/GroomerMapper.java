package ddog.groomer.application.mapper;

import ddog.domain.account.Status;
import ddog.domain.groomer.Groomer;
import ddog.groomer.presentation.account.dto.ModifyInfoReq;
import ddog.groomer.presentation.account.dto.ProfileInfo;
import ddog.groomer.presentation.account.dto.SignUpReq;

public class GroomerMapper {

    public static Groomer create(Long accountId, SignUpReq request) {
        return Groomer.builder()
                .accountId(accountId)
                .status(Status.PENDING)
                .daengleMeter(0)
                .groomerName(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .address(request.getAddress())
                .detailAddress(request.getDetailAddress())
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

    public static Groomer withUpdate(Groomer groomer, ModifyInfoReq request) {
        return Groomer.builder()
                .groomerId(groomer.getGroomerId())
                .accountId(groomer.getAccountId())
                .status(groomer.getStatus())
                .daengleMeter(groomer.getDaengleMeter())
                .groomerName(groomer.getGroomerName())
                .phoneNumber(groomer.getPhoneNumber())
                .groomerImage(request.getImage())
                .email(groomer.getEmail())
                .address(groomer.getAddress())
                .shopName(groomer.getShopName())
                .groomerIntroduction(request.getIntroduction())
                .businessLicenses(groomer.getBusinessLicenses())
                .licenses(groomer.getLicenses())
                .build();
    }
}
