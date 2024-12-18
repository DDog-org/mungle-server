package ddog.user.presentation.estimate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.domain.estimate.Proposal;
import ddog.domain.vet.enums.CareBadge;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CareEstimateDetail {

    private Long careEstimateId;
    private Long vetId;
    private String imageUrl;
    private String name;
    private int daengleMeter;
    private List<CareBadge> badges;
    private Proposal proposal;
    private String introduction;
    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedDate;
    private String diagnosis;
    private String cause;
    private String treatment;

}
