package ddog.vet.presentation.schedule.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class ScheduleResp {

    public Integer totalScheduleCount;
    public Integer designationCount;
    public Integer totalReservationCount;
    List<TodayReservation> todayAllReservations;

    @Getter
    @Builder
    public static class TodayReservation {
        public Long petId;
        public String petImage;
        public String petName;
        public String desiredStyle;
        public Long estimateId;
        public LocalTime reservationTime;
    }
}
