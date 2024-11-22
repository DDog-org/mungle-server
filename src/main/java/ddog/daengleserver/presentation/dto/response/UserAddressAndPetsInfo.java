package ddog.daengleserver.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserAddressAndPetsInfo {

    private String address;
    private List<PetInfo> petInfos;

    @Getter
    @Builder
    public static class PetInfo {
        private String petImage;
        private String petName;
    }
}
