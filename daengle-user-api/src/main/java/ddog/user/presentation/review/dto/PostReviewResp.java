package ddog.user.presentation.review.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostReviewResp {
    private Long reviewId;
    private Long reviewerId;
    private Long revieweeId;
}



//TODO 리뷰에 받는 사람 id 있어야됨... 그루머 id, vet id