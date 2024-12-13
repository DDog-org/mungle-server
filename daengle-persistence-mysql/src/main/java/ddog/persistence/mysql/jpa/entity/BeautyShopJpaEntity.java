package ddog.persistence.mysql.jpa.entity;

import ddog.domain.shop.BeautyShop;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "BeautyShops")
public class BeautyShopJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;
    private String shopName;
    private String shopAddress;

    @ElementCollection
    @CollectionTable(name = "ShopImages", joinColumns = @JoinColumn(name = "shop_id"))
    @Column(name = "image_url_list")
    private List<String> imageUrlList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id")
    private List<GroomerJpaEntity> groomers;

    private LocalTime startTime;
    private LocalTime endTime;

    private String introduction;

    private String imageUrl;

    public static BeautyShopJpaEntity from (BeautyShop beautyShop) {
        return BeautyShopJpaEntity.builder()
                .shopId(beautyShop.getShopId())
                .shopName(beautyShop.getShopName())
                .shopAddress(beautyShop.getShopAddress())
                .imageUrlList(beautyShop.getImageUrlList())
                .groomers(beautyShop.getGroomers()
                        .stream().map(GroomerJpaEntity::from)
                        .toList())
                .startTime(beautyShop.getStartTime())
                .endTime(beautyShop.getEndTime())
                .introduction(beautyShop.getIntroduction())
                .imageUrl(beautyShop.getImageUrl())
                .build();
    }

    public BeautyShop toModel() {
        return BeautyShop.builder()
                .shopId(shopId)
                .shopName(shopName)
                .shopAddress(shopAddress)
                .imageUrlList(imageUrlList)
                .groomers(groomers.stream().map(GroomerJpaEntity::toModel).toList())
                .startTime(startTime)
                .endTime(endTime)
                .introduction(introduction)
                .imageUrl(imageUrl)
                .build();
    }

}
