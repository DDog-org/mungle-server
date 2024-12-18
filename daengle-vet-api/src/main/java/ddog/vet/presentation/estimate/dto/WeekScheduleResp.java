package ddog.vet.presentation.estimate.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class WeekScheduleResp {
    private LocalDate scheduleDate;
    private List<VetSchedule> scheduleList;

    @Getter
    @Builder
    public static class VetSchedule {
        private LocalDateTime scheduleTime;
        private Long reservationId;
        private Long petId;
        private String petName;
        private String petProfile;
    }
}