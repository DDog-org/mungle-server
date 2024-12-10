package ddog.groomer.presentation.review;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.groomer.application.ReviewService;
import ddog.groomer.presentation.review.dto.ReviewListResp;
import ddog.groomer.presentation.review.dto.ReviewSummaryResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ddog.auth.exception.common.CommonResponseEntity.success;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groomer/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/list")
    public CommonResponseEntity<ReviewListResp> findReviewList(PayloadDto payloadDto,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        return success(reviewService.findReviewList(payloadDto.getAccountId(), page, size));
    }
}
