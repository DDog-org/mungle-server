package ddog.daengleserver.presentation.estimate.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.daengleserver.domain.Weight;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserGeneralGroomingEstimateReq {

    private String address;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedDate;
    private Long petId;
    private String petImage;
    private String petName;
    private String petSignificant;
    private int petBirth;
    private Weight petWeight;

    private String desiredStyle;
    private String requirements;
    private String userImage;
    private String nickname;

}
