package ddog.user.application;

import ddog.domain.beauty.BeautyShop;
import ddog.domain.beauty.port.BeautyShopPersist;
import ddog.domain.user.port.UserPersist;
import ddog.user.application.mapper.BeautyShopMapper;
import ddog.user.presentation.beauty.dto.BeautyShopResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeautyShopService {
    private final BeautyShopPersist beautyShopPersist;
    private final UserPersist userPersist;

    public List<BeautyShopResp.BeautyShop> convertToBeautyShopList(String address, Long accountId) {
        if(address == null) address = userPersist.findByAccountId(accountId).get().getAddress();

        List<BeautyShop> findBeautyShops = beautyShopPersist.findBeautyShopsByAddressPrefix(address);

        return findBeautyShops.stream()
                .map(BeautyShopMapper::mapToBeautyShop)
                .collect(Collectors.toList());

    }

    public BeautyShopResp findBeautyShops(Long accountId, String address) {
        List<BeautyShopResp.BeautyShop> beautyShops = convertToBeautyShopList(address, accountId);
        return BeautyShopResp.builder()
                .allShops(beautyShops)
                .build();
    }
}
