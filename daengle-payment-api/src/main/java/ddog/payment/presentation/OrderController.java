package ddog.payment.presentation;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.domain.order.enums.PostOrderReq;
import ddog.payment.application.OrderService;
import ddog.payment.application.dto.response.PostOrderResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.auth.exception.common.CommonResponseEntity.success;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daengle")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public CommonResponseEntity<PostOrderResp> processOrder(PayloadDto payloadDto, @RequestBody PostOrderReq postOrderReq) {
        return success(orderService.processOrder(payloadDto.getAccountId(), postOrderReq));
    }
}
