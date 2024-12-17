package ddog.user.presentation.search.dto;

import ddog.domain.vet.enums.CareKeyword;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchVetResultByKeyword {
    List<ResultList> result;

    @Getter
    @Builder
    public static class ResultList {
        public Long partnerId;
        public String partnerName;
        public String partnerImage;
        public List<CareKeyword> careKeywords;
    }
}
