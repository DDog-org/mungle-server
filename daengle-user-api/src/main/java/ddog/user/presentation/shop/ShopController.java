package ddog.user.presentation.shop;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.user.application.ShopService;
import ddog.user.presentation.shop.dto.ShopResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.auth.exception.common.CommonResponseEntity.success;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/list")
public class ShopController {
    private final ShopService shopService;

    @GetMapping("/shops")
    public CommonResponseEntity<ShopResp> getBeautyShopsList(@RequestParam(required = false) String address, PayloadDto payloadDto) {
        return success(shopService.findBeautyShops(payloadDto.getAccountId(), address));
    }

    @GetMapping("/vets")
    public CommonResponseEntity<ShopResp> getVetsList(@RequestParam(required = false) String address, PayloadDto payloadDto) {
        return success(shopService.findVets(payloadDto.getAccountId(), address));
    }

}
