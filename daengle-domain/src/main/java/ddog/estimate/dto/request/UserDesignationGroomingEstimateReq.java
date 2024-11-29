package ddog.estimate.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.pet.Weight;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserDesignationGroomingEstimateReq {

    private Long groomerId;
    private String address;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedDate;
    private Long petId;
    private String petImage;
    private String name;
    private String significant;
    private int birth;
    private Weight weight;

    private String desiredStyle;
    private String requirements;
    private String userImage;
    private String nickname;

}
