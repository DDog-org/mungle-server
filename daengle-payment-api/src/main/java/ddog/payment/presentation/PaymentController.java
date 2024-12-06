package ddog.payment.presentation;

import ddog.auth.exception.common.CommonResponseEntity;
import ddog.payment.application.PaymentService;
import ddog.payment.application.dto.request.PaymentCallbackReq;
import ddog.payment.application.dto.response.PaymentCallbackResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
