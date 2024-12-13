package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.BeautyShopJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BeautyShopJpaRepository extends JpaRepository<BeautyShopJpaEntity, Long> {
    @Query("SELECT b FROM BeautyShops b " +
            "WHERE b.shopAddress = :address")
    List<BeautyShopJpaEntity> findBeautyShopsByShopAddress(@Param("address") String address);

    @Query("SELECT b FROM BeautyShops b WHERE b.shopAddress LIKE CONCAT(:addressPrefix, '%')")
    List<BeautyShopJpaEntity> findBeautyShopsByAddressPrefix(@Param("addressPrefix") String addressPrefix);

    Optional<BeautyShopJpaEntity> findByShopId(Long id);
}
