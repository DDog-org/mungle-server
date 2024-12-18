package ddog.domain.shop.port;

import ddog.domain.shop.BeautyShop;
import ddog.domain.shop.dto.UpdateShopReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BeautyShopPersist {
    void save(BeautyShop beautyShop);

    Page<BeautyShop> findBeautyShopsByAddress(String address, Pageable pageable);

    List<BeautyShop> findBeautyShopsByAddressPrefix(String addressPrefix);

    BeautyShop findBeautyShopById(Long shopId);

    Optional<BeautyShop> findBeautyShopByNameAndAddress(String name, String address);

    void updateBeautyShopWithUpdateShopReq(UpdateShopReq request);
}
