package ddog.user.presentation.account.dto;

import ddog.domain.pet.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PetInfo {
    List<Detail> petDetails;

    @Getter
    @Builder
    public static class Detail {
        private Long id;
        private String image;
        private String name;
        private int birth;
        private Gender gender;
        private Breed breed;
        private Boolean isNeutered;
        private Weight weight;
        private Boolean groomingExperience;
        private Boolean isBite;
        private List<PartDetail> dislikeParts;
        private List<TagDetail> significantTags;
        private String significant;

        @Getter
        @Builder
        public static class PartDetail {
            private String partName;
            private String part;
        }

        @Getter
        @Builder
        public static class TagDetail {
            private String tagName;
            private String tag;
        }
    }
}
