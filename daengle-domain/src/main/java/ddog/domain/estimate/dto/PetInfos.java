package ddog.domain.estimate.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PetInfos {

    private List<Content> pets;

    @Builder
    @Getter
    public static class Content {
        private Long estimateId;
        private Long petId;
        private String imageUrl;
        private String name;
    }
}
