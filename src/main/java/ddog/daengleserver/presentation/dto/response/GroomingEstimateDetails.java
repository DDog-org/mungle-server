package ddog.daengleserver.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.daengleserver.domain.Weight;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GroomingEstimateDetails {

    private Long groomingEstimateId;
    private String userImage;
    private String nickname;
    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedDate;

    private String petImage;
    private int petBirth;
    private Weight petWeight;
    private String petSignificant;
    private String petName;
    private String desiredStyle;
    private String requirements;

}
