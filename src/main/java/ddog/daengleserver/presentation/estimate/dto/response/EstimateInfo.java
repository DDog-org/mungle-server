package ddog.daengleserver.presentation.estimate.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class EstimateInfo {

    List<PetEstimate> petEstimates;

    @Getter
    @Builder
    public static class PetEstimate {
        private String petName;
        private String petImage;
        List<GroomingEstimateInfo> groomingEstimates;
        List<CareEstimateInfo> careEstimates;

        @Getter
        @Builder
        public static class GroomingEstimateInfo {
            private Long groomingEstimateId;
            private String groomerName;
            private String groomerImage;
            private String shopName;

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            private LocalDateTime reservedDate;
        }

        @Getter
        @Builder
        public static class CareEstimateInfo {
            private Long careEstimateId;
            private String vetName;
            private String vetImage;

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            private LocalDateTime reservedDate;
        }
    }

}
