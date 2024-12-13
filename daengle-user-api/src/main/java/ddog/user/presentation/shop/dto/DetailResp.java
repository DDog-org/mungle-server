package ddog.user.presentation.shop.dto;

import ddog.domain.groomer.GroomerSummaryInfo;
import ddog.domain.groomer.enums.GroomingKeyword;
import ddog.domain.vet.enums.CareKeyword;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class DetailResp {

    List<ShopInfo> allShops;
    List<VetInfo> allVets;

    @Getter
    @Builder
    public static class ShopInfo {
        private Long shopId;
        private String shopName;
        private String shopAddress;
        private String imageUrl;
        private LocalTime startTime;
        private LocalTime endTime;
    }

    @Getter
    @Builder
    public static class ShopDetailInfo {
        private Long shopId;
        private String shopName;
        private String shopAddress;
        private List<String> imageUrlList;
        private List<GroomerSummaryInfo> groomers;
        private LocalTime startTime;
        private LocalTime endTime;
        private String introduction;
        private Long reviewCount;
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

    @Getter
    @Builder
    public static class VetDetailInfo {
        private Long vetId;
        private String vetName;
        private String vetAddress;
        private String vetImage;
        private LocalTime startTime;
        private LocalTime endTime;
        private List<CareKeyword> keywords;
        private String introductions;
        private int daengleMeter;
        private Long reviewCount;
    }

    @Getter
    @Builder
    public static class GroomerDetailInfo {
        private Long groomerId;
        private String groomerName;
        private List<GroomingKeyword> keywords;
        private String introduction;
        private Long shopId;
        private String shopName;
        private int daengleMeter;
        private Long reviewCount;
        private String urlLink;
    }
}
