package ddog.groomer.presentation.account.dto;

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
        private List<String> imageUrlList;
        private String shopName;
        private LocalTime startTime;
        private LocalTime endTime;
        private List<Day> closedDays;
        private String phoneNumber;
        private String address;
        private String detailAddress;
        private String introduction;
    }

}
