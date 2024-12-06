package ddog.user.presentation.review;

import ddog.auth.exception.common.CommonResponseEntity;
import ddog.domain.review.dto.PostCareReviewInfo;
import ddog.user.application.CareReviewService;
import ddog.user.presentation.review.dto.PostReviewResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/review")
public class CareReviewController {

    private final CareReviewService careReviewService;

    @PostMapping
    public CommonResponseEntity<PostReviewResp> postReview(@RequestBody PostCareReviewInfo postCareReviewInfo) {
        return success(careReviewService.postReview(postCareReviewInfo));
    }

}
