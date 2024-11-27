package ddog.daengleserver.presentation.account.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BreedInfos {

    private List<Detail> breedList;

    @Getter
    @Builder
    public static class Detail {
        private String breedName;
        private String breed;
    }

}
