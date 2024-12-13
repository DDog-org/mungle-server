package ddog.groomer.presentation.detailInfo;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.groomer.application.DetailInfoService;
import ddog.groomer.presentation.detailInfo.dto.DetailResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.auth.exception.common.CommonResponseEntity.success;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groomer")
public class DetailInfoController {
    private final DetailInfoService detailInfoService;

    @GetMapping("/shops")
    public CommonResponseEntity<DetailResp> getBeautyShopsList(@RequestParam(required = false) String address, PayloadDto payloadDto) {
        return success(detailInfoService.findBeautyShops(payloadDto.getAccountId(), address));
    }

    @GetMapping("/vets")
    public CommonResponseEntity<DetailResp> getVetsList(@RequestParam(required = false) String address, PayloadDto payloadDto) {
        return success(detailInfoService.findVets(payloadDto.getAccountId(), address));
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
