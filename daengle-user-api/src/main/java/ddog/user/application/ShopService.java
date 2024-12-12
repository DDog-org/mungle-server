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

    private String extractDistrict(String address) {
        if (address == null) return null;
        String[] parts = address.split(" ");
        if (parts.length >= 3) {
            return String.join(" ", parts[0], parts[1], parts[2]);
        }
        return address;
    }

    public List<ShopResp.ShopInfo> convertToBeautyShopList(String address, Long accountId) {
        if (address == null) {
            address = userPersist.findByAccountId(accountId).orElseThrow(() ->
                    new IllegalArgumentException("사용자 주소를 찾을 수 없습니다.")
            ).getAddress();
        }
        String districtAddress = extractDistrict(address);
        List<BeautyShop> findBeautyShops = beautyShopPersist.findBeautyShopsByAddressPrefix(districtAddress);

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
        if (address == null) {
            address = userPersist.findByAccountId(accountId).orElseThrow(() ->
                    new IllegalArgumentException("사용자 주소를 찾을 수 없습니다.")
            ).getAddress();
        }
        String districtAddress = extractDistrict(address);
        List<Vet> findVets = vetPersist.findByAddressPrefix(districtAddress);

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
