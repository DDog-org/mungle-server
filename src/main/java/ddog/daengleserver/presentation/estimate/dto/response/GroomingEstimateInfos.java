package ddog.daengleserver.presentation.estimate.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.daengleserver.domain.estimate.GroomingEstimate;
import ddog.daengleserver.domain.estimate.Proposal;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GroomingEstimateInfos {

    List<Contents> allGroomingEstimates;
    List<Contents> designationGroomingEstimates;

    @Getter
    @Builder
    public static class Contents {
        private Long groomingEstimateId;
        private String userImage;
        private String nickname;
        private Proposal proposal;
        private String petSignificant;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime reservedDate;
    }

}
