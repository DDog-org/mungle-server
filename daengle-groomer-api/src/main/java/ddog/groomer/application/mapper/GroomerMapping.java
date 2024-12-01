package ddog.groomer.application.mapper;

import ddog.domain.groomer.Groomer;
import ddog.groomer.presentation.account.dto.SignUpReq;

public class GroomerMapping {

    public static Groomer create(Long accountId, SignUpReq request) {
        return Groomer.builder()
                .accountId(accountId)
                .daengleMeter(0)
                .groomerName(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .shopName(request.getShopName())
                .businessLicenses(request.getBusinessLicenses())
                .licenses(request.getLicenses())
                .build();
    }
}
