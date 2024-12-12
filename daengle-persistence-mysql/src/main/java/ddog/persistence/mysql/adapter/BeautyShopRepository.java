package ddog.persistence.mysql.adapter;

import ddog.domain.beauty.BeautyShop;
import ddog.domain.beauty.port.BeautyShopPersist;
import ddog.persistence.mysql.jpa.entity.BeautyShopJpaEntity;
import ddog.persistence.mysql.jpa.repository.BeautyShopJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BeautyShopRepository implements BeautyShopPersist {

    private final BeautyShopJpaRepository beautyShopJpaRepository;

    @Override
    public List<BeautyShop> findBeautyShops(String address) {
        List<BeautyShopJpaEntity> beautyShops = beautyShopJpaRepository.findBeautyShopsByShopAddress(address);

        return beautyShops.stream()
                .map(BeautyShopJpaEntity::toModel)
                .collect(Collectors.toList());
    }

    public List<BeautyShop> findBeautyShopsByAddressPrefix(String addressPrefix) {
        List<BeautyShopJpaEntity> beautyShops = beautyShopJpaRepository.findBeautyShopsByAddressPrefix(addressPrefix);

        return beautyShops.stream()
                .map(BeautyShopJpaEntity::toModel)
                .collect(Collectors.toList());
    }

}
