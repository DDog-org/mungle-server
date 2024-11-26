package ddog.daengleserver.presentation.dto.response;

import ddog.daengleserver.domain.Breed;
import ddog.daengleserver.domain.Gender;
import ddog.daengleserver.domain.Weight;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinPetInfo {

    private String petName;
    private int petBirth;
    private Gender petGender;
    private Weight petWeight;
    private Breed breed;

}
