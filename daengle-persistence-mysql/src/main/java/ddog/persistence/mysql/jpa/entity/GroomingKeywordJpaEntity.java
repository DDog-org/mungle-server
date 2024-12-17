package ddog.persistence.mysql.jpa.entity;

import ddog.domain.groomer.GroomingKeyword;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GroomingKeywords")
public class GroomingKeywordJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordId;
    private Long accountId;
    private String keyword;
    private Integer count;

    public static GroomingKeywordJpaEntity from(GroomingKeyword keyword) {
        return GroomingKeywordJpaEntity.builder()
                .keywordId(keyword.getKeywordId())
                .accountId(keyword.getAccountId())
                .keyword(keyword.getKeyword())
                .count(keyword.getCount())
                .build();
    }

    public GroomingKeyword toModel() {
        return GroomingKeyword.builder()
                .keywordId(keywordId)
                .accountId(accountId)
                .keyword(keyword)
                .count(count)
                .build();
    }
}
