package ddog.daengleserver.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GroomerGroomingEstimateReq {

    private Long groomingEstimateId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedDate;
    private String overallOpinion;

}
