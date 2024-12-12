package ddog.domain.shop.port;

import ddog.domain.shop.BeautyShop;

import java.util.List;

public interface BeautyShopPersist {
    List<BeautyShop> findBeautyShops(String address);
    List<BeautyShop> findBeautyShopsByAddressPrefix(String addressPrefix);
}
