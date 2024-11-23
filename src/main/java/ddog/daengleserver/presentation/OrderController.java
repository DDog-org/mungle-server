package ddog.daengleserver.presentation;

import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.dto.request.PostOrderReq;
import ddog.daengleserver.presentation.enums.PostOrderResp;
import ddog.daengleserver.presentation.usecase.OrderUseCase;
import ddog.daengleserver.presentation.usecase.PaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daengle")
public class OrderController {

    private final OrderUseCase orderUseCase;

    @PostMapping("/order")
    public CommonResponseEntity<PostOrderResp> registerOrder(@RequestBody PostOrderReq postOrderReq) {
        orderUseCase.registerOrder(postOrderReq);
        return success(PostOrderResp.ORDER_REGISTRATION_COMPLETED);
    }
}
