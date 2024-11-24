package ddog.daengleserver.application;

import ddog.daengleserver.presentation.dto.request.PostOrderReq;
import ddog.daengleserver.presentation.usecase.PaymentUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService implements PaymentUseCase {
}
