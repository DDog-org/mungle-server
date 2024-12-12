package ddog.user.application;

import ddog.domain.shop.BeautyShop;
import ddog.domain.shop.port.BeautyShopPersist;
import ddog.domain.user.port.UserPersist;
import ddog.domain.vet.Vet;
import ddog.domain.vet.port.VetPersist;
import ddog.user.application.mapper.ShopMapper;
import ddog.user.presentation.shop.dto.ShopResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final BeautyShopPersist beautyShopPersist;
    private final UserPersist userPersist;
    private final VetPersist vetPersist;

    public List<ShopResp.ShopInfo> convertToBeautyShopList(String address, Long accountId) {
        if(address == null) address = userPersist.findByAccountId(accountId).get().getAddress();

        List<BeautyShop> findBeautyShops = beautyShopPersist.findBeautyShopsByAddressPrefix(address);

        return findBeautyShops.stream()
                .map(ShopMapper::mapToBeautyShop)
                .collect(Collectors.toList());

    }

    public ShopResp findBeautyShops(Long accountId, String address) {
        List<ShopResp.ShopInfo> shopInfos = convertToBeautyShopList(address, accountId);
        return ShopResp.builder()
                .allShops(shopInfos)
                .build();
    }

    public List<ShopResp.VetInfo> convertToVetList(String address, Long accountId) {
        if(address == null) address = userPersist.findByAccountId(accountId).get().getAddress();

        List<Vet> findVets = vetPersist.findByAddressPrefix(address);

        return findVets.stream()
                .map(ShopMapper::mapToVet)
                .collect(Collectors.toList());

    }

    public ShopResp findVets(Long accountId, String address) {
        List<ShopResp.VetInfo> vetInfos = convertToVetList(address, accountId);
        return ShopResp.builder()
                .allVets(vetInfos)
                .build();
    }


}
