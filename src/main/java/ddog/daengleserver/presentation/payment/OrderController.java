package ddog.daengleserver.presentation.payment;

import ddog.daengleserver.global.auth.dto.PayloadDto;
import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.dto.request.PostOrderReq;
import ddog.daengleserver.presentation.dto.response.PostOrderResp;
import ddog.daengleserver.presentation.usecase.OrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class OrderController {

    private final OrderUseCase orderUseCase;

    @PostMapping("/order")
    public CommonResponseEntity<PostOrderResp> processOrder(PayloadDto payloadDto, @RequestBody PostOrderReq postOrderReq) {
        return success(orderUseCase.processOrder(payloadDto.getAccountId(), postOrderReq));
    }
}
