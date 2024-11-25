package ddog.daengleserver.presentation.estimate.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.daengleserver.domain.Weight;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserDesignationCareEstimateReq {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedDate;
    private String symptoms;
    private String requirements;

    private Long userId;
    private String userImage;
    private String nickname;
    private String address;

    private Long petId;
    private String petImage;
    private String petName;
    private String petSignificant;
    private int petBirth;
    private Weight petWeight;

    private Long vetId;

}
