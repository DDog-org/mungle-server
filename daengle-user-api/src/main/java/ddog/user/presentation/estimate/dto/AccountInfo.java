package ddog.user.presentation.estimate.dto;

import ddog.domain.pet.Weight;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AccountInfo {

    @Getter
    @Builder
    public static class Grooming{
        private String groomerImage;
        private String groomerName;
        private String shopName;

        private String userImage;
        private String nickname;
        private String address;
        private List<PetInfo> petInfos;
    }

    @Getter
    @Builder
    public static class Care {
        private String vetImage;
        private String vetName;

        private String userImage;
        private String nickname;
        private String address;
        private List<PetInfo> petInfos;
    }

    @Getter
    @Builder
    public static class PetInfo {
        private Long id;
        private String image;
        private String name;
        private String significant;
        private int birth;
        private Weight weight;
    }
}
