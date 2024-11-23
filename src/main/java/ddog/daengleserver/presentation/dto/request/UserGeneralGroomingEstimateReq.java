package ddog.daengleserver.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.daengleserver.domain.Weight;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserGeneralGroomingEstimateReq {

    private Long userId;
    private Long petId;
    private String userImage;
    private String petImage;
    private String nickname;
    private String petSignificant;
    private int petBirth;
    private Weight petWeight;
    private String petName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedDate;
    private String desiredStyle;
    private String requirements;
    private String address;

}
