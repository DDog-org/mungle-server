package ddog.user.application.mapper;

import ddog.domain.shop.BeautyShop;
import ddog.domain.vet.Vet;
import ddog.user.presentation.shop.dto.ShopResp;

public class ShopMapper {

    public static ShopResp.ShopInfo mapToBeautyShop(BeautyShop beautyShop){
        return ShopResp.ShopInfo.builder()
                .shopId(beautyShop.getShopId())
                .shopName(beautyShop.getShopName())
                .shopAddress(beautyShop.getShopAddress())
                .imageUrlList(beautyShop.getImageUrlList())
                .groomers(beautyShop.getGroomers())
                .startTime(beautyShop.getStartTime())
                .endTime(beautyShop.getEndTime())
                .build();
    }

    public static ShopResp.VetInfo mapToVet(Vet vet){
        return ShopResp.VetInfo.builder()
                .vetId(vet.getVetId())
                .vetName(vet.getVetName())
                .vetAddress(vet.getAddress())
                .vetImage(vet.getVetImage())
                .startTime(vet.getStartTime())
                .endTime(vet.getEndTime())
                .build();
    }
}
