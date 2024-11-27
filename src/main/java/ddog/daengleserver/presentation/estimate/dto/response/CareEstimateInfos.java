package ddog.daengleserver.presentation.estimate.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.daengleserver.domain.estimate.Proposal;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CareEstimateInfos {

    List<Contents> allCareEstimates;
    List<Contents> designationCareEstimates;

    @Getter
    @Builder
    public static class Contents {
        private Long careEstimateId;
        private String userImage;
        private String nickname;
        private Proposal proposal;
        private String petSignificant;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime reservedDate;
    }
}
