package ddog.user.application.mapper;

import ddog.domain.beauty.BeautyShop;
import ddog.user.presentation.beauty.dto.BeautyShopResp;

import java.util.ArrayList;
import java.util.List;

public class BeautyShopMapper {

    public static BeautyShopResp.BeautyShop mapToBeautyShop(BeautyShop beautyShop){
        return BeautyShopResp.BeautyShop.builder()
                .shopId(beautyShop.getShopId())
                .shopName(beautyShop.getShopName())
                .shopAddress(beautyShop.getShopAddress())
                .imageUrlList(beautyShop.getImageUrlList())
                .groomers(beautyShop.getGroomers())
                .startTime(beautyShop.getStartTime())
                .endTime(beautyShop.getEndTime())
                .build();
    }

}
