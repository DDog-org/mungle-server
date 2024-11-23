package ddog.daengleserver.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GeneralGroomingEstimateReq {

    private Long userId;
    private String userImage;
    private String nickname;
    private Long petId;
    private String petSignificant;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedDate;
    private String desiredStyle;
    private String requirements;
    private String address;

}
