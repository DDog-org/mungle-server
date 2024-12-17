package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.BeautyShopJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface BeautyShopJpaRepository extends JpaRepository<BeautyShopJpaEntity, Long> {
    @Query("SELECT s FROM BeautyShops s " +
            "WHERE REPLACE(s.shopAddress, ' ', '') LIKE CONCAT('%', REPLACE(:address, ' ', ''), '%')")
    Page<BeautyShopJpaEntity> findBeautyShopsByShopAddress(@Param("address") String address, Pageable pageable);

    @Query("SELECT s FROM BeautyShops s WHERE s.shopAddress LIKE CONCAT(:addressPrefix, '%')")
    List<BeautyShopJpaEntity> findBeautyShopsByAddressPrefix(@Param("addressPrefix") String addressPrefix);

    Optional<BeautyShopJpaEntity> findByShopId(Long id);

    Optional<BeautyShopJpaEntity> findBeautyShopJpaEntityByShopNameAndShopAddress(String shopName, String address);

    @Modifying
    @Query("UPDATE BeautyShops " +
            "SET startTime = :startTime, endTime = :endTime, phoneNumber = :phoneNumber, introduction = :introduction " +
            "WHERE shopId = :shopId")
    void updateTimeAndPhoneNumberAndIntroduction(LocalTime startTime, LocalTime endTime, String phoneNumber, String introduction, Long shopId);

    @Modifying
    @Query(value = "DELETE FROM shop_images WHERE shop_id = :shopId", nativeQuery = true)
    void deleteShopImagesByShopId(Long shopId);

    @Modifying
    @Query(value = "INSERT INTO shop_images (shop_id, image_url_list) VALUES (:shopId, :imageUrl)", nativeQuery = true)
    void createShopImagesWithImages(String imageUrl, Long shopId);

    @Modifying
    @Query(value = "DELETE FROM shop_closed_days WHERE shop_id = :shopId", nativeQuery = true)
    void deleteShopClosedDaysByShopId(Long shopId);

    @Modifying
    @Query(value = "INSERT INTO shop_closed_days (shop_id, day) VALUES (:shopId, :day)", nativeQuery = true)
    void createShopClosedDaysWithClosedDays(String day, Long shopId);
}
