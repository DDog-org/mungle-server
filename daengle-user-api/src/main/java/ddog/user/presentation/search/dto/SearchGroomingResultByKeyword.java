package ddog.user.presentation.search.dto;

import ddog.domain.groomer.enums.GroomingBadge;
import ddog.domain.groomer.enums.GroomingKeyword;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchGroomingResultByKeyword {
    private int page;
    private int size;
    private long totalElements;

    List<ResultList> result;

    @Getter
    @Builder
    public static class ResultList {
        public Long partnerId;
        public String partnerName;
        public String partnerImage;
        public List<GroomingBadge> groomingBadges;
    }
}
