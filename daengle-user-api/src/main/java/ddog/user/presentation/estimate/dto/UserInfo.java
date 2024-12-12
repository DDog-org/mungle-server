package ddog.user.presentation.estimate.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserInfo {

    @Getter
    @Builder
    public static class Grooming {
        private String groomerImageUrl;
        private String groomerName;
        private String shopName;

        private String address;
        private List<PetInfo> petInfos;
    }

    @Getter
    @Builder
    public static class Care {
        private String vetImageUrl;
        private String vetName;

        private String address;
        private List<PetInfo> petInfos;
    }

    @Getter
    @Builder
    public static class PetInfo {
        private Long petId;
        private String imageUrl;
        private String name;
    }
}
