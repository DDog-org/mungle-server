package ddog.daengleserver.presentation.payment;

import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.dto.request.PaymentCallbackReq;
import ddog.daengleserver.presentation.dto.response.PaymentCallbackResp;
import ddog.daengleserver.presentation.usecase.PaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    @PostMapping("/validate")
    public CommonResponseEntity<PaymentCallbackResp> validationPayment(@RequestBody PaymentCallbackReq paymentCallbackReq) {
        return success(paymentUseCase.validationPayment(paymentCallbackReq));
    }
}
