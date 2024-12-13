package ddog.domain.shop.port;

import ddog.domain.shop.BeautyShop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BeautyShopPersist {
    Page<BeautyShop> findBeautyShopsByAddress(String address, Pageable pageable);
    List<BeautyShop> findBeautyShopsByAddressPrefix(String addressPrefix);
    BeautyShop findBeautyShopById(Long shopId);
    BeautyShop findBeautyShopByNameAndAddress(String name, String address);
}
