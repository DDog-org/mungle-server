package ddog.user.presentation.account.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BreedInfo {

    private List<Detail> breedList;

    @Getter
    @Builder
    public static class Detail {
        private String breedName;
        private String breed;
    }

}
