package ddog.vet.presentation.estimate.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class WeekScheduleResp {
    private String scheduleDate;
    private List<VetSchedule> scheduleList;

    @Getter
    @Builder
    public static class VetSchedule {
        private LocalTime scheduleTime;
        private Long reservationId;
        private Long petId;
        private String petName;
        private String petProfile;
    }
}