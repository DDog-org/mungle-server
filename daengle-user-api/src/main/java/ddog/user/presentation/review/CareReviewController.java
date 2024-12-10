package ddog.user.presentation.review;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.domain.review.dto.ModifyCareReviewInfo;
import ddog.domain.review.dto.PostCareReviewInfo;
import ddog.user.application.CareReviewService;
import ddog.user.presentation.review.dto.CareReviewDetaliResp;
import ddog.user.presentation.review.dto.ReviewResp;
import ddog.user.presentation.review.dto.CareReviewSummaryResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class CareReviewController {

    private final CareReviewService careReviewService;

    @PostMapping("/care/review")
    public CommonResponseEntity<ReviewResp> postReview(@RequestBody PostCareReviewInfo postCareReviewInfo) {
        return success(careReviewService.postReview(postCareReviewInfo));
    }

    @GetMapping("/review/care/{reviewId}")
    public CommonResponseEntity<CareReviewDetaliResp> getReview(@PathVariable Long reviewId) {
        return success(careReviewService.getReview(reviewId));
    }

    @PatchMapping("care/review/{reviewId}")
    public CommonResponseEntity<ReviewResp> modifyReview(@PathVariable Long reviewId,
                                                         @RequestBody ModifyCareReviewInfo modifyCareReviewInfo) {
        return success(careReviewService.modifyReview(reviewId, modifyCareReviewInfo));
    }

    @DeleteMapping("/care/review/{reviewId}")
    public CommonResponseEntity<ReviewResp> deleteReview(@PathVariable Long reviewId) {
        return success(careReviewService.deleteReview(reviewId));
    }

    @GetMapping("care/my-review/list")
    public CommonResponseEntity<List<CareReviewSummaryResp>> findMyReviewList(PayloadDto payloadDto,
                                                                              @RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size) {
        return success(careReviewService.findMyReviewList(payloadDto.getAccountId(), page, size));
    }

    @GetMapping("/vet/{vetId}/review/list")
    public CommonResponseEntity<List<CareReviewSummaryResp>> findVetReviewList(
            @PathVariable Long vetId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return success(careReviewService.findVetReviewList(vetId, page, size));
    }
}
