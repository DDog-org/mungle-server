package ddog.user.presentation.review;

import ddog.auth.exception.common.CommonResponseEntity;
import ddog.domain.review.dto.ModifyCareReviewInfo;
import ddog.domain.review.dto.PostCareReviewInfo;
import ddog.user.application.CareReviewService;
import ddog.user.presentation.review.dto.ModifyReviewResp;
import ddog.user.presentation.review.dto.PostReviewResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/review/care")
public class CareReviewController {

    private final CareReviewService careReviewService;

    @PostMapping
    public CommonResponseEntity<PostReviewResp> postReview(@RequestBody PostCareReviewInfo postCareReviewInfo) {
        return success(careReviewService.postReview(postCareReviewInfo));
    }

    @PatchMapping
    public CommonResponseEntity<ModifyReviewResp> modifyReview(@RequestBody ModifyCareReviewInfo modifyCareReviewInfo) {
        return success(careReviewService.modifyReview(modifyCareReviewInfo));
    }
}
