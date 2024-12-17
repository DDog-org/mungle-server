package ddog.user.application.mapper;

import ddog.domain.shop.BeautyShop;
import ddog.domain.vet.Vet;
import ddog.user.presentation.detailInfo.dto.DetailResp;

public class DetailInfoMapper {

    public static DetailResp.ShopInfo mapToBeautyShop(BeautyShop beautyShop){
        return DetailResp.ShopInfo.builder()
                .shopId(beautyShop.getShopId())
                .shopName(beautyShop.getShopName())
                .shopAddress(beautyShop.getShopAddress())
                .shopImage(beautyShop.getImageUrl())
                .startTime(beautyShop.getStartTime())
                .endTime(beautyShop.getEndTime())
                .closedDay(beautyShop.getClosedDays())
                .build();
    }

    public static DetailResp.VetInfo mapToVet(Vet vet){
        return DetailResp.VetInfo.builder()
                .vetAccountId(vet.getAccountId())
                .vetName(vet.getName())
                .vetAddress(vet.getAddress())
                .vetImage(vet.getImageUrl())
                .startTime(vet.getStartTime())
                .endTime(vet.getEndTime())
                .closedDay(vet.getClosedDays())
                .build();
    }
}
