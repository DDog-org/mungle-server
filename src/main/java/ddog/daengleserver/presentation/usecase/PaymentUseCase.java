package ddog.daengleserver.presentation.usecase;

import ddog.daengleserver.presentation.dto.request.PaymentCallbackReq;
import ddog.daengleserver.presentation.dto.response.PaymentCallbackResp;

public interface PaymentUseCase {

    PaymentCallbackResp validationPayment(PaymentCallbackReq paymentCallbackReq);
}
