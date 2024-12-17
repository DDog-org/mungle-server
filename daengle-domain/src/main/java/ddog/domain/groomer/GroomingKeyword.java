package ddog.domain.groomer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroomingKeyword {

    private Long keywordId;
    private Long accountId;
    private String keyword;
    private Integer count;

}