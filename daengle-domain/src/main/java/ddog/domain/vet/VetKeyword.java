package ddog.domain.vet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VetKeyword {

    private Long keywordId;
    private Long accountId;
    private String keyword;
    private Integer count;

}
