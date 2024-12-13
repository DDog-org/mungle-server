package ddog.domain.shop.port;

import ddog.domain.shop.BeautyShop;

import java.util.List;

public interface BeautyShopPersist {
    List<BeautyShop> findBeautyShopsByAddress(String address);
    List<BeautyShop> findBeautyShopsByAddressPrefix(String addressPrefix);
    BeautyShop findBeautyShopById(Long shopId);
}
