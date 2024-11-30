package ddog.user.presentation.estimate.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CareEstimateDetail {

    private String image;
    private String name;
    private int daengleMeter;
    private String introduction;
    private String address;
    private LocalDateTime reservedDate;
    private String diagnosis;
    private String cause;
    private String treatment;

}
