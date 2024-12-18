package ddog.domain.groomer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroomerKeyword {

    private Long keywordId;
    private Long accountId;
    private String keyword;
    private Integer count;

    public static GroomerKeyword createNewKeyword(Long accountId, String keyword) {
        return GroomerKeyword.builder()
                .accountId(accountId)
                .keyword(keyword)
                .count(1)
                .build();
    }

    public void increaseCount() {
        count++;
    }

    public boolean isAvailableRegisterBadge() {
        return count == 10;
    }
}