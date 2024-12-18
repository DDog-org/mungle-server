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

    public static VetKeyword createNewKeyword(Long accountId, String keyword) {
        return VetKeyword.builder()
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
