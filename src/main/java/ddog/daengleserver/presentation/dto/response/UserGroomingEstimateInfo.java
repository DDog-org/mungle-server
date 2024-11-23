package ddog.daengleserver.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserGroomingEstimateInfo {

    private Long groomingEstimateId;
    private String nickname;
    private String userImage;
    private String address;
    private String petSignificant;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedDate;
}
