package ddog.persistence.rdb.jpa.entity;

import ddog.domain.vet.VetKeyword;
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
public class VetKeywordJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordId;

    @Column(name = "account_id")
    private Long accountId;
    private String keyword;
    private Integer count;

    public static VetKeywordJpaEntity from(VetKeyword vetKeyword) {
        return VetKeywordJpaEntity.builder()
                .keywordId(vetKeyword.getKeywordId())
                .accountId(vetKeyword.getAccountId())
                .keyword(vetKeyword.getKeyword())
                .count(vetKeyword.getCount())
                .build();
    }

    public VetKeyword toModel() {
        return VetKeyword.builder()
                .keywordId(keywordId)
                .accountId(accountId)
                .keyword(keyword)
                .count(count)
                .build();
    }
}
