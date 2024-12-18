package ddog.user.presentation.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.domain.groomer.enums.GroomingBadge;
import ddog.domain.pet.Weight;
import ddog.domain.vet.enums.CareBadge;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class EstimateDetail {

    @Getter
    @Builder
    public static class Grooming {
        private Long reservationId;
        private Long groomingEstimateId;
        private Long groomerId;
        private String imageUrl;
        private String name;
        private Long shopId;
        private String shopName;
        private int daengleMeter;
        private List<GroomingBadge> badges;
        private String introduction;
        private String address;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime reservedDate;
        private Weight weight;
        private String desiredStyle;
        private String overallOpinion;
    }

    @Getter
    @Builder
    public static class Care {
        private Long reservationId;
        private Long careEstimateId;
        private Long vetId;
        private String imageUrl;
        private String name;
        private int daengleMeter;
        private List<CareBadge> badges;
        private String introduction;
        private String address;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime reservedDate;
        private String diagnosis;
        private String cause;
        private String treatment;
    }

}
