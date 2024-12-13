package ddog.user.application.mapper;

import ddog.domain.shop.BeautyShop;
import ddog.domain.vet.Vet;
import ddog.user.presentation.shop.dto.DetailResp;

public class ShopMapper {

    public static DetailResp.ShopInfo mapToBeautyShop(BeautyShop beautyShop){
        return DetailResp.ShopInfo.builder()
                .shopId(beautyShop.getShopId())
                .shopName(beautyShop.getShopName())
                .shopAddress(beautyShop.getShopAddress())
                .imageUrlList(beautyShop.getImageUrlList())
                .startTime(beautyShop.getStartTime())
                .endTime(beautyShop.getEndTime())
                .build();
    }

    public static DetailResp.VetInfo mapToVet(Vet vet){
        return DetailResp.VetInfo.builder()
                .vetId(vet.getVetId())
                .vetName(vet.getName())
                .vetAddress(vet.getAddress())
                .vetImage(vet.getImageUrl())
                .startTime(vet.getStartTime())
                .endTime(vet.getEndTime())
                .build();
    }
}
