package ddog.daengleserver.presentation.estimate.dto.response;

import ddog.daengleserver.domain.account.enums.Weight;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserInfo {

    private String userImage;
    private String nickname;
    private String address;
    private List<PetInfo> petInfos;

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
