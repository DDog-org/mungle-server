package ddog.persistence.mysql.jpa.entity;

import ddog.domain.vet.CareKeyword;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CareKeywords")
public class CareKeywordJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordId;

    @Column(name = "account_id")
    private Long accountId;
    private String keyword;
    private Integer count;

    public static CareKeywordJpaEntity from(CareKeyword careKeyword) {
        return CareKeywordJpaEntity.builder()
                .keywordId(careKeyword.getKeywordId())
                .accountId(careKeyword.getAccountId())
                .keyword(careKeyword.getKeyword())
                .count(careKeyword.getCount())
                .build();
    }

    public CareKeyword toModel() {
        return CareKeyword.builder()
                .keywordId(keywordId)
                .accountId(accountId)
                .keyword(keyword)
                .count(count)
                .build();
    }
}
