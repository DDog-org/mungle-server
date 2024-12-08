package ddog.user.presentation.review;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.domain.review.dto.ModifyGroomingReviewInfo;
import ddog.domain.review.dto.PostGroomingReviewInfo;
import ddog.user.application.GroomingReviewService;
import ddog.user.presentation.review.dto.GroomingReviewSummaryResp;
import ddog.user.presentation.review.dto.ReviewResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/review")
public class GroomingReviewController {

    private final GroomingReviewService groomingReviewService;

    @PostMapping("/grooming")
    public CommonResponseEntity<ReviewResp> postReview(@RequestBody PostGroomingReviewInfo postGroomingReviewInfo) {
        return success(groomingReviewService.postReview(postGroomingReviewInfo));
    }

    @PatchMapping("/grooming")
    public CommonResponseEntity<ReviewResp> modifyReview(@RequestBody ModifyGroomingReviewInfo modifyGroomingReviewInfo) {
        return success(groomingReviewService.modifyReview(modifyGroomingReviewInfo));
    }

    @DeleteMapping("/grooming/{reviewId}")
    public CommonResponseEntity<ReviewResp> deleteReview(@PathVariable Long reviewId) {
        return success(groomingReviewService.deleteReview(reviewId));
    }

    @GetMapping("/grooming/list")
    public CommonResponseEntity<List<GroomingReviewSummaryResp>> findMyReviewList(PayloadDto payloadDto,
                                                                                  @RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = "10") int size) {
        return success(groomingReviewService.findMyReviewList(payloadDto.getAccountId(), page, size));
    }

}
