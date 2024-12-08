package ddog.user.presentation.review;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.domain.review.dto.ModifyCareReviewInfo;
import ddog.domain.review.dto.PostCareReviewInfo;
import ddog.user.application.CareReviewService;
import ddog.user.presentation.review.dto.ReviewResp;
import ddog.user.presentation.review.dto.ReviewSummaryResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/review/care")
public class CareReviewController {

    private final CareReviewService careReviewService;

    @PostMapping
    public CommonResponseEntity<ReviewResp> postReview(@RequestBody PostCareReviewInfo postCareReviewInfo) {
        return success(careReviewService.postReview(postCareReviewInfo));
    }

    @PatchMapping
    public CommonResponseEntity<ReviewResp> modifyReview(@RequestBody ModifyCareReviewInfo modifyCareReviewInfo) {
        return success(careReviewService.modifyReview(modifyCareReviewInfo));
    }

    @DeleteMapping("/{reviewId}")
    public CommonResponseEntity<ReviewResp> deleteReview(@PathVariable Long reviewId) {
        return success(careReviewService.deleteReview(reviewId));
    }

    @GetMapping("/list")
    public CommonResponseEntity<List<ReviewSummaryResp>> findReviewList(PayloadDto payloadDto,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        return success(careReviewService.findReviewList(payloadDto.getAccountId(), page, size));
    }
}
