package ddog.user.presentation.detailInfo;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.user.application.DetailInfoService;
import ddog.user.presentation.detailInfo.dto.DetailResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.auth.exception.common.CommonResponseEntity.success;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class DetailInfoController {
    private final DetailInfoService detailInfoService;

    @GetMapping("/shops")
    public CommonResponseEntity<DetailResp> getBeautyShopsList(@RequestParam(required = false) String address, PayloadDto payloadDto,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "5") int size) {
        return success(detailInfoService.findBeautyShops(payloadDto.getAccountId(), address, page, size));
    }

    @GetMapping("/vets")
    public CommonResponseEntity<DetailResp> getVetsList(@RequestParam(required = false) String address,
                                                        PayloadDto payloadDto,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size){
        return success(detailInfoService.findVets(payloadDto.getAccountId(), address, page, size));
    }

    @GetMapping("/shop/{shopId}")
    public CommonResponseEntity<DetailResp.ShopDetailInfo> getBeautyShopDetail(@PathVariable Long shopId) {
        return success(detailInfoService.findBeautyShop(shopId));
    }

    @GetMapping("/vet/{vetId}")
    public CommonResponseEntity<DetailResp.VetDetailInfo> getVetDetail(@PathVariable Long vetId) {
        return success(detailInfoService.findVet(vetId));
    }

    @GetMapping("/groomer/{groomerId}")
    public CommonResponseEntity<DetailResp.GroomerDetailInfo> getGroomerDetail(@PathVariable Long groomerId) {
        return success(detailInfoService.findGroomerById(groomerId));
    }
}
