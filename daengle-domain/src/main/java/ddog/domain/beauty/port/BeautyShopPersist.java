package ddog.domain.beauty.port;

import ddog.domain.beauty.BeautyShop;

import java.util.List;

public interface BeautyShopPersist {
    List<BeautyShop> findBeautyShops(String address);
    List<BeautyShop> findBeautyShopsByAddressPrefix(String addressPrefix);
}
