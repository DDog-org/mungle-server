package ddog.persistence.rdb.jpa.entity;

import ddog.domain.shop.BeautyShop;
import ddog.domain.vet.Day;
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
    private String shopDetailAddress;
    private String phoneNumber;
    private String imageUrl;

    @ElementCollection
    @CollectionTable(name = "ShopImages", joinColumns = @JoinColumn(name = "shop_id"))
    @Column(name = "image_url_list")
    private List<String> imageUrlList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopId")
    private List<GroomerJpaEntity> groomers;

    private LocalTime startTime;
    private LocalTime endTime;

    @ElementCollection // 휴무일 리스트
    @CollectionTable(name = "shop_closed_days", joinColumns = @JoinColumn(name = "shop_id"))
    @Column(name = "day")
    @Enumerated(EnumType.STRING)
    private List<Day> closedDays;

    private String introduction;

    public static BeautyShopJpaEntity from(BeautyShop beautyShop) {
        return BeautyShopJpaEntity.builder()
                .shopId(beautyShop.getShopId())
                .shopName(beautyShop.getShopName())
                .shopAddress(beautyShop.getShopAddress())
                .shopDetailAddress(beautyShop.getShopDetailAddress())
                .phoneNumber(beautyShop.getPhoneNumber())
                .imageUrl(beautyShop.getImageUrl())
                .imageUrlList(beautyShop.getImageUrlList())
                .groomers(beautyShop.getGroomers()
                        .stream().map(GroomerJpaEntity::from)
                        .toList())
                .startTime(beautyShop.getStartTime())
                .endTime(beautyShop.getEndTime())
                .closedDays(beautyShop.getClosedDays())
                .introduction(beautyShop.getIntroduction())
                .build();
    }

    public BeautyShop toModel() {
        return BeautyShop.builder()
                .shopId(shopId)
                .shopName(shopName)
                .shopAddress(shopAddress)
                .shopDetailAddress(shopDetailAddress)
                .phoneNumber(phoneNumber)
                .imageUrl(imageUrl)
                .imageUrlList(imageUrlList)
                .groomers(groomers.stream().map(GroomerJpaEntity::toModel).toList())
                .startTime(startTime)
                .endTime(endTime)
                .closedDays(closedDays)
                .introduction(introduction)
                .build();
    }

}
