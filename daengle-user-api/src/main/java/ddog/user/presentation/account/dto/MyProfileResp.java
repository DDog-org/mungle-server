package ddog.user.presentation.account.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyProfileResp {
    Long id;
    String image;
    String nickname;
    Integer reviewCount;
    Integer estimateCount;
    List<PetSummaryInfo> petInfos;

    @Getter
    @Builder
    public static class PetSummaryInfo {
        Long petId;
        String petImage;
        String petName;
    }
}
