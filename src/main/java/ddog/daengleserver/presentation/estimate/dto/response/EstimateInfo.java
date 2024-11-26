package ddog.daengleserver.presentation.estimate.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class EstimateInfo {

    List<PetInfo> petInfos;

    @Getter
    @Builder
    public static class PetInfo {
        private String petName;
        private String petImage;
        List<Grooming> groomingEstimates;
        List<Care> careEstimates;

        @Getter
        @Builder
        public static class Grooming {
            private Long groomingEstimateId;
            private String groomerName;
            private int daengleMeter;
            private String groomerImage;
            private String shopName;

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            private LocalDateTime reservedDate;
        }

        @Getter
        @Builder
        public static class Care {
            private Long careEstimateId;
            private String vetName;
            private int daengleMeter;
            private String vetImage;

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            private LocalDateTime reservedDate;
        }
    }

}
