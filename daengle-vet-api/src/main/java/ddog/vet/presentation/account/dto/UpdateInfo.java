package ddog.vet.presentation.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.domain.vet.Day;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
public class UpdateInfo {
    private List<String> imageUrls;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime endTime;

    private List<Day> closedDays;
    private String phoneNumber;
    private String introduction;
}
