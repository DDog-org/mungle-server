package ddog.persistence.mysql.adapter;

import ddog.domain.shop.BeautyShop;
import ddog.domain.shop.port.BeautyShopPersist;
import ddog.persistence.mysql.jpa.entity.BeautyShopJpaEntity;
import ddog.persistence.mysql.jpa.repository.BeautyShopJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BeautyShopRepository implements BeautyShopPersist {

    private final BeautyShopJpaRepository beautyShopJpaRepository;

    @Override
    public void save(BeautyShop beautyShop) {
        System.out.println("save--------");
        beautyShopJpaRepository.save(BeautyShopJpaEntity.from(beautyShop));
    }

    @Override
    public Page<BeautyShop> findBeautyShopsByAddress(String address, Pageable pageable){
        Page<BeautyShopJpaEntity> beautyShops = beautyShopJpaRepository.findBeautyShopsByShopAddress(address, pageable);

        return beautyShops.map(BeautyShopJpaEntity::toModel);
    }

    public List<BeautyShop> findBeautyShopsByAddressPrefix(String addressPrefix) {
        List<BeautyShopJpaEntity> beautyShops = beautyShopJpaRepository.findBeautyShopsByAddressPrefix(addressPrefix);

        return beautyShops.stream()
                .map(BeautyShopJpaEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public BeautyShop findBeautyShopById(Long shopId) {
        return beautyShopJpaRepository.findByShopId(shopId).orElseThrow().toModel();
    }

    @Override
    public BeautyShop findBeautyShopByNameAndAddress(String name, String address) {
        return beautyShopJpaRepository.findBeautyShopJpaEntityByShopNameAndShopAddress(name, address).orElseThrow().toModel();
    }

}
