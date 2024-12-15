package ddog.vet.presentation.review;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.vet.application.ReviewService;
import ddog.vet.presentation.review.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ddog.auth.exception.common.CommonResponseEntity.success;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vet/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/list")
    public CommonResponseEntity<ReviewListResp> findReviewList(PayloadDto payloadDto,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        return success(reviewService.findReviewList(payloadDto.getAccountId(), page, size));
    }

    @PostMapping("/report")
    public CommonResponseEntity<ReportReviewResp> reportReview(@RequestBody ReportReviewReq reportReviewReq) {
        return success(reviewService.reportReview(reportReviewReq));
    }

//    @GetMapping("/report/list")
//    public CommonResponseEntity<ReportedReviewListResp> findReportedReviewList(PayloadDto payloadDto)
}
