package ddog.user.presentation.estimate.dto;

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
        private String name;
        private String image;
        List<Grooming> groomingEstimates;
        List<Care> careEstimates;

        @Getter
        @Builder
        public static class Grooming {
            private Long id;
            private String name;
            private int daengleMeter;
            private String image;
            private String shopName;

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            private LocalDateTime reservedDate;
        }

        @Getter
        @Builder
        public static class Care {
            private Long id;
            private String name;
            private int daengleMeter;
            private String image;

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            private LocalDateTime reservedDate;
        }
    }

}
