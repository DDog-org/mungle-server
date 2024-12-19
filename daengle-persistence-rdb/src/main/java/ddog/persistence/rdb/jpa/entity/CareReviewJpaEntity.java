package ddog.persistence.rdb.jpa.entity;

import ddog.domain.review.CareReview;
import ddog.domain.vet.enums.CareKeyword;
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
    private Long vetId;
    private Long reservationId;
    private Long reviewerId;
    private Long revieweeId;
    private String revieweeName;
    private String shopName;
    private Integer starRating;
    private String content;
    private LocalDateTime createdAt;

    @ElementCollection // 이미지 URL 리스트
    @CollectionTable(name = "care_review_image_url_list", joinColumns = @JoinColumn(name = "care_review_id"))
    @Column(name = "image_url_list")
    private List<String> imageUrlList;

    @ElementCollection // 키워드 리스트
    @CollectionTable(name = "care_review_keyword_list", joinColumns = @JoinColumn(name = "care_review_id"))
    @Column(name = "keyword_list")
    @Enumerated(EnumType.STRING)
    private List<CareKeyword> careKeywordList;

    public static CareReviewJpaEntity from (CareReview careReview) {
        return CareReviewJpaEntity.builder()
                .careReviewId(careReview.getCareReviewId())
                .vetId(careReview.getVetId())
                .reservationId(careReview.getReservationId())
                .reviewerId(careReview.getReviewerId())
                .revieweeId(careReview.getVetId())
                .revieweeName(careReview.getRevieweeName())
                .shopName(careReview.getShopName())
                .starRating(careReview.getStarRating())
                .content(careReview.getContent())
                .createdAt(careReview.getCreatedAt())
                .imageUrlList(careReview.getImageUrlList())
                .careKeywordList(careReview.getCareKeywordList())
                .build();
    }

    public CareReview toModel() {
        return CareReview.builder()
                .careReviewId(careReviewId)
                .vetId(vetId)
                .reservationId(reservationId)
                .reviewerId(reviewerId)
                .revieweeName(revieweeName)
                .shopName(shopName)
                .starRating(starRating)
                .content(content)
                .createdAt(createdAt)
                .imageUrlList(imageUrlList)
                .careKeywordList(careKeywordList)
                .build();
    }
}
