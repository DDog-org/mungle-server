package ddog.persistence.jpa.entity;

import ddog.domain.review.CareReview;
import ddog.domain.review.enums.CareKeywordReview;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CareReviews")
public class CareReviewJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long careReviewId;
    private Long reviewerId;
    private Long revieweeId;
    private Long reviewCount;
    private Long starRating;
    private String content;
    private LocalDateTime createTime;

    @ElementCollection // 이미지 URL 리스트
    @CollectionTable(name = "care_review_image_url_list", joinColumns = @JoinColumn(name = "care_review_id"))
    @Column(name = "image_url_list")
    private List<String> imageUrlList;

    @ElementCollection // 키워드 리스트
    @CollectionTable(name = "care_review_keyword_list", joinColumns = @JoinColumn(name = "care_review_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "keyword_list")
    private List<CareKeywordReview> careKeywordReviewList;

    public static CareReviewJpaEntity from (CareReview careReview) {
        return CareReviewJpaEntity.builder()
                .careReviewId(careReview.getCareReviewId())
                .reviewerId(careReview.getReviewerId())
                .revieweeId(careReview.getRevieweeId())
                .reviewCount(careReview.getReviewCount())
                .starRating(careReview.getStarRating())
                .content(careReview.getContent())
                .createTime(careReview.getCreateTime())
                .imageUrlList(careReview.getImageUrlList())
                .careKeywordReviewList(careReview.getCareKeywordReviewList())
                .build();
    }

    public CareReview toModel() {
        return CareReview.builder()
                .careReviewId(careReviewId)
                .reviewerId(reviewerId)
                .revieweeId(revieweeId)
                .reviewCount(reviewCount)
                .starRating(starRating)
                .content(content)
                .createTime(createTime)
                .imageUrlList(imageUrlList)
                .careKeywordReviewList(careKeywordReviewList)
                .build();
    }
}
