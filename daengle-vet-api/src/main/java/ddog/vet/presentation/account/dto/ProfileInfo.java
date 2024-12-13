package ddog.vet.presentation.account.dto;

import ddog.domain.vet.Day;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class ProfileInfo {

    @Getter
    @Builder
    public static class UpdatePage {
        private String image;
        private String name;
        private LocalTime startTime;
        private LocalTime endTime;
        private List<Day> closedDays;
        private String phoneNumber;
        private String address;
        private String detailAddress;
        private String introduction;
    }
}
