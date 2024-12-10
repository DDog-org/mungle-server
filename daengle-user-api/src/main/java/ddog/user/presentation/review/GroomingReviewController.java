package ddog.user.presentation.review;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.user.presentation.review.dto.ModifyGroomingReviewInfo;
import ddog.user.presentation.review.dto.PostGroomingReviewInfo;
import ddog.user.application.GroomingReviewService;
import ddog.user.presentation.review.dto.GroomingReviewDetailResp;
import ddog.user.presentation.review.dto.GroomingReviewSummaryResp;
import ddog.user.presentation.review.dto.ReviewResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class GroomingReviewController {

    private final GroomingReviewService groomingReviewService;

    @PostMapping("/grooming/review")
    public CommonResponseEntity<ReviewResp> postReview(@RequestBody PostGroomingReviewInfo postGroomingReviewInfo) {
        return success(groomingReviewService.postReview(postGroomingReviewInfo));
    }

    @GetMapping("/grooming/review/{reviewId}")
    public CommonResponseEntity<GroomingReviewDetailResp> findReview(@PathVariable Long reviewId) {
        return success(groomingReviewService.findReview(reviewId));
    }

    @PatchMapping("/grooming/review/{reviewId}")
    public CommonResponseEntity<ReviewResp> modifyReview(@PathVariable Long reviewId,
                                                         @RequestBody ModifyGroomingReviewInfo modifyGroomingReviewInfo) {
        return success(groomingReviewService.modifyReview(reviewId, modifyGroomingReviewInfo));
    }

    @DeleteMapping("/grooming/review/{reviewId}")
    public CommonResponseEntity<ReviewResp> deleteReview(@PathVariable Long reviewId) {
        return success(groomingReviewService.deleteReview(reviewId));
    }

    @GetMapping("/grooming/my-review/list")
    public CommonResponseEntity<List<GroomingReviewSummaryResp>> findMyReviewList(PayloadDto payloadDto,
                                                                                  @RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = "10") int size) {
        return success(groomingReviewService.findMyReviewList(payloadDto.getAccountId(), page, size));
    }

    @GetMapping("/groomer/{groomerId}/review/list")
    public CommonResponseEntity<List<GroomingReviewSummaryResp>> findGroomerReviewList(
            @PathVariable Long groomerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return success(groomingReviewService.findGroomerReviewList(groomerId, page, size));
    }
}
