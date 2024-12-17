package ddog.user.presentation.detailInfo.dto;

import ddog.domain.groomer.GroomerSummaryInfo;
import ddog.domain.groomer.enums.GroomingKeyword;
import ddog.domain.vet.Day;
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
    int totalPages;
    Long totalElements;
    int currentPage;
    private String userAddress;

    @Getter
    @Builder
    public static class ShopInfo {
        private Long shopId;
        private String shopName;
        private String shopAddress;
        private String shopImage;
        private LocalTime startTime;
        private LocalTime endTime;
        private List<Day> closedDay;
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
        private List<Day> closedDay;
    }

    @Getter
    @Builder
    public static class VetInfo {
        private Long vetAccountId;
        private String vetName;
        private String vetAddress;
        private String vetImage;
        private LocalTime startTime;
        private LocalTime endTime;
        private List<Day> closedDay;
    }

    @Getter
    @Builder
    public static class VetDetailInfo {
        private Long vetAccountId;
        private String vetName;
        private String vetAddress;
        private String vetImage;
        private LocalTime startTime;
        private LocalTime endTime;
        private List<CareKeyword> keywords;
        private String introduction;
        private int daengleMeter;
        private Long reviewCount;
        private List<String> imageUrlList;
        private String vetNumber;
        private List<Day> closedDay;
    }

    @Getter
    @Builder
    public static class GroomerDetailInfo {
        private Long groomerAccountId;
        private String groomerName;
        private String groomerImage;
        private List<GroomingKeyword> keywords;
        private String introduction;
        private Long shopId;
        private String shopName;
        private int daengleMeter;
        private Long reviewCount;
        private String instagram;
    }
}
