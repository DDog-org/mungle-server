package ddog.vet.presentation.account.dto;

import ddog.domain.vet.Day;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
public class UpdateInfo {
    private List<String> imageUrls;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<Day> closedDays;
    private String phoneNumber;
    private String introduction;
}
