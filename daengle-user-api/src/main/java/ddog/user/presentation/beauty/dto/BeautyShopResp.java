package ddog.user.presentation.beauty.dto;

import ddog.domain.groomer.Groomer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class BeautyShopResp {

    List<BeautyShop> allShops;

    @Getter
    @Builder
    public static class BeautyShop {
        private Long shopId;
        private String shopName;
        private String shopAddress;
        private List<String> imageUrlList;
        private List<Groomer> groomers;
        private LocalTime startTime;
        private LocalTime endTime;
    }
}
