package ddog.groomer.presentation.estimate.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class WeekScheduleResp {
    private String scheduleDate;
    private List<GroomerSchedule> scheduleList;

    @Getter
    @Builder
    public static class GroomerSchedule {
        private LocalTime scheduleTime;
        private Long reservationId;
        private Long petId;
        private String petName;
        private String petProfile;
        private String desiredStyle;
    }

}
