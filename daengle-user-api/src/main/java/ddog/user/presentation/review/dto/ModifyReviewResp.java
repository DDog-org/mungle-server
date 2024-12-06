package ddog.user.presentation.review.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ModifyReviewResp {
    private Long reviewId;
    private Long reviewerId;
    private Long revieweeId;
}
