package ddog.vet.presentation.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.domain.vet.Day;
import ddog.domain.vet.enums.CareBadge;
import ddog.domain.vet.enums.CareKeyword;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class ProfileInfo {

    private List<String> imageUrls;
    private String name;
    private List<CareBadge> badges;
    private List<Day> closedDays;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime endTime;

    private String phoneNumber;
    private String address;
    private String detailAddress;
    private String introduction;
    private int daengleMeter;

    @Getter
    @Builder
    public static class UpdatePage {
        private List<String> imageUrls;
        private String name;

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
}
