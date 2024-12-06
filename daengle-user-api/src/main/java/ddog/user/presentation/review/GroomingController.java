package ddog.user.presentation.review;

import ddog.auth.exception.common.CommonResponseEntity;
import ddog.domain.review.dto.ModifyGroomingReviewInfo;
import ddog.domain.review.dto.PostGroomingReviewInfo;
import ddog.user.application.GroomingReviewService;
import ddog.user.presentation.review.dto.ModifyReviewResp;
import ddog.user.presentation.review.dto.PostReviewResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/review/grooming")
public class GroomingController {

    private final GroomingReviewService groomingReviewService;

    @PostMapping
    public CommonResponseEntity<PostReviewResp> postReview(@RequestBody PostGroomingReviewInfo postGroomingReviewInfo) {
        return success(groomingReviewService.postReview(postGroomingReviewInfo));
    }

    @PatchMapping
    public CommonResponseEntity<ModifyReviewResp> modifyReview(@RequestBody ModifyGroomingReviewInfo modifyGroomingReviewInfo) {
        return success(groomingReviewService.modifyReview(modifyGroomingReviewInfo));
    }
}
