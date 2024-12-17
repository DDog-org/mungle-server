package ddog.groomer.presentation.estimate.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class WeekScheduleResp {
    private LocalDate scheduleDate;
    private List<GroomerSchedule> scheduleList;

    @Getter
    @Builder
    public static class GroomerSchedule {
        private LocalDateTime scheduleTime;
        private Long petId;
        private String petName;
        private String petProfile;
        private String desiredStyle;
    }
}
