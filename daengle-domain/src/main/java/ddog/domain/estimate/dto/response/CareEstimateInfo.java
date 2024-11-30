package ddog.domain.estimate.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.domain.estimate.Proposal;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CareEstimateInfo {

    List<Content> allEstimates;
    List<Content> designationEstimates;

    @Getter
    @Builder
    public static class Content {
        private Long id;
        private String image;
        private String nickname;
        private Proposal proposal;
        private String significant;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime reservedDate;
    }
}
