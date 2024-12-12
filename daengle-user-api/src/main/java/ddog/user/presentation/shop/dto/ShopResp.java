package ddog.user.presentation.shop.dto;

import ddog.domain.groomer.Groomer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class ShopResp {

    List<ShopInfo> allShops;
    List<VetInfo> allVets;

    @Getter
    @Builder
    public static class ShopInfo {
        private Long shopId;
        private String shopName;
        private String shopAddress;
        private List<String> imageUrlList;
        private List<Groomer> groomers;
        private LocalTime startTime;
        private LocalTime endTime;
    }

    @Getter
    @Builder
    public static class VetInfo {
        private Long vetId;
        private String vetName;
        private String vetAddress;
        private String vetImage;
        private LocalTime startTime;
        private LocalTime endTime;
    }
}
