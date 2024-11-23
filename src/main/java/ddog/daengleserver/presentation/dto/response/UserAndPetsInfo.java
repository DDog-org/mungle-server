package ddog.daengleserver.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserAndPetsInfo {

    private String userImage;
    private String nickname;
    private String address;
    private List<PetInfo> petInfos;

    @Getter
    @Builder
    public static class PetInfo {
        private Long petId;
        private String petImage;
        private String petName;
        private String petSignificant;

    }
}
