package ddog.daengleserver.presentation;

import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.dto.request.PostOrderReq;
import ddog.daengleserver.presentation.dto.response.PostOrderResp;
import ddog.daengleserver.presentation.usecase.OrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daengle")
public class OrderController {

    private final OrderUseCase orderUseCase;

    @PostMapping("/order")
    public CommonResponseEntity<PostOrderResp> processOrder(@RequestBody PostOrderReq postOrderReq) {
        return success(orderUseCase.processOrder(postOrderReq));
    }
}
