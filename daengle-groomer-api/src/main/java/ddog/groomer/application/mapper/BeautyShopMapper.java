package ddog.groomer.application.mapper;

import ddog.domain.groomer.Groomer;
import ddog.domain.shop.BeautyShop;
import ddog.groomer.presentation.account.dto.ShopInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

public class BeautyShopMapper {

    @Getter
    public class UpdateShopInfo {
        private Long shopId;

        private String shopName;
        private String shopAddress;
        private String imageUrl;
        private List<String> imageUrlList;
        private List<Groomer> groomers;
        private LocalTime startTime;
        private LocalTime endTime;
        private String introduction;
    }

    public static BeautyShop updateShopInfo(Long shopId, UpdateShopInfo req){
        return BeautyShop.builder()
                .shopId(shopId)
                .shopName(req.getShopName())
                .shopAddress(req.getShopAddress())
                .imageUrl(req.getImageUrl())
                .imageUrlList(req.getImageUrlList())
                .groomers(req.getGroomers())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .introduction(req.getIntroduction())
                .build();
    }

    @Getter
    @Builder
    public static class UpdateInfoResp {
        private String requestResult;
    }

    public static ShopInfo.UpdatePage mapToShopInfo(BeautyShop shop) {
        return ShopInfo.UpdatePage.builder()
                .imageUrlList(shop.getImageUrlList())
                .shopName(shop.getShopName())
                .startTime(shop.getStartTime())
                .endTime(shop.getEndTime())
                .closedDays(shop.getClosedDays())
                .phoneNumber(shop.getPhoneNumber())
                .address(shop.getShopAddress())
                .detailAddress(shop.getShopDetailAddress())
                .introduction(shop.getIntroduction())
                .build();
    }
}
