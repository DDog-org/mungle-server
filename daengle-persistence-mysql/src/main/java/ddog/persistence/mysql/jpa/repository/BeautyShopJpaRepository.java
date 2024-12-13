package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.BeautyShopJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BeautyShopJpaRepository extends JpaRepository<BeautyShopJpaEntity, Long> {
    @Query("SELECT b FROM BeautyShops b " +
            "WHERE b.shopAddress = :address")
    Page<BeautyShopJpaEntity> findBeautyShopsByShopAddress(@Param("address") String address, Pageable pageable);

    @Query("SELECT b FROM BeautyShops b WHERE b.shopAddress LIKE CONCAT(:addressPrefix, '%')")
    List<BeautyShopJpaEntity> findBeautyShopsByAddressPrefix(@Param("addressPrefix") String addressPrefix);

    Optional<BeautyShopJpaEntity> findByShopId(Long id);

    Optional<BeautyShopJpaEntity> findBeautyShopJpaEntityByShopNameAndShopAddress(String shopName, String address);
}
