package ddog.user.presentation.beauty;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.user.application.BeautyShopService;
import ddog.user.presentation.beauty.dto.BeautyShopResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static ddog.auth.exception.common.CommonResponseEntity.success;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/shops")
public class BeautyShopController {
    private final BeautyShopService beautyShopService;

    @GetMapping
    public CommonResponseEntity<BeautyShopResp> getBeautyShopsList(@RequestParam(required = false) String address, PayloadDto payloadDto) {
        return success(beautyShopService.findBeautyShops(payloadDto.getAccountId(),address));
    }
}
