package ddog.groomer.presentation.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.domain.vet.Day;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class ShopInfo {

    @Getter
    @Builder
    public static class UpdatePage {
        private Long shopId;
        private List<String> imageUrlList;
        private String shopName;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime startTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime endTime;
        private List<Day> closedDays;
        private String phoneNumber;
        private String address;
        private String detailAddress;
        private String introduction;
    }

    @Getter
    @Builder
    public static class UpdateResp {
        private String requestResp;
    }

}
