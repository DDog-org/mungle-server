package ddog.user.presentation.estimate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.domain.estimate.Proposal;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CareEstimateDetail {

    private Long careEstimateId;
    private Long vetId;
    private String imageURL;
    private String name;
    private int daengleMeter;
    private Proposal proposal;
    private String introduction;
    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedDate;
    private String diagnosis;
    private String cause;
    private String treatment;

}
