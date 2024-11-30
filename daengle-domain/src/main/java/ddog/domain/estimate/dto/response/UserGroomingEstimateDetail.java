package ddog.domain.estimate.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.domain.pet.Weight;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserGroomingEstimateDetail {

    private String userImage;
    private String nickname;
    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedDate;

    private Long id;
    private String petImage;
    private String name;
    private int birth;
    private Weight weight;
    private String significant;
    private String desiredStyle;
    private String requirements;

}
