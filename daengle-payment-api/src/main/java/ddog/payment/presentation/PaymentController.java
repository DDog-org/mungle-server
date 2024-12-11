package ddog.payment.presentation;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.payment.application.PaymentService;
import ddog.payment.application.dto.request.PaymentCallbackReq;
import ddog.payment.application.dto.response.PaymentCallbackResp;
import ddog.payment.application.dto.response.PaymentHistoryListResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.auth.exception.common.CommonResponseEntity.success;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/validate")
    public CommonResponseEntity<PaymentCallbackResp> validationPayment(@RequestBody PaymentCallbackReq paymentCallbackReq) {
        return success(paymentService.validationPayment(paymentCallbackReq));
    }

    @GetMapping("/grooming/history/list")
    public CommonResponseEntity<PaymentHistoryListResp> findGroomingPaymentHistory(
            PayloadDto payloadDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return success(paymentService.findGroomingPaymentHistory(payloadDto.getAccountId(), page, size));
    }
}
