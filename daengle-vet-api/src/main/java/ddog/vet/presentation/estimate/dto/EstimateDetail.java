package ddog.vet.presentation.estimate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.domain.pet.Weight;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EstimateDetail {

    private String userImage;
    private String nickname;
    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedDate;

    private Long petId;
    private String petImage;
    private String petName;
    private int birth;
    private Weight weight;
    private String significant;
    private String symptoms;
    private String requirements;

}
