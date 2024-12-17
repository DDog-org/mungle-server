package ddog.persistence.mysql.jpa.entity;

import ddog.domain.groomer.GroomerKeyword;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GroomingKeywords")
public class GroomerKeywordJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordId;

    @Column(name = "account_id")
    private Long accountId;
    private String keyword;
    private Integer count;

    public static GroomerKeywordJpaEntity from(GroomerKeyword keyword) {
        return GroomerKeywordJpaEntity.builder()
                .keywordId(keyword.getKeywordId())
                .accountId(keyword.getAccountId())
                .keyword(keyword.getKeyword())
                .count(keyword.getCount())
                .build();
    }

    public GroomerKeyword toModel() {
        return GroomerKeyword.builder()
                .keywordId(keywordId)
                .accountId(accountId)
                .keyword(keyword)
                .count(count)
                .build();
    }
}
