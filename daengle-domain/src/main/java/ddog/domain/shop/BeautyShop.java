package ddog.domain.shop;

import ddog.domain.groomer.Groomer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeautyShop {
    private Long shopId;
    private String shopName;
    private String shopAddress;
    private String imageUrl;
    private List<String> imageUrlList;
    private List<Groomer> groomers;
    private LocalTime startTime;
    private LocalTime endTime;
    private String introduction;

    public static BeautyShop create(String shopName, String shopAddress){
        return BeautyShop.builder()
                .shopName(shopName)
                .shopAddress(shopAddress)
                .groomers(new ArrayList<>())
                .build();
    }

    public BeautyShop addGroomer(Groomer groomer) {
        if (groomers == null) {
            groomers = new ArrayList<>();
        }
        groomers.add(groomer);

        return BeautyShop.builder()
                .shopId(shopId)
                .shopName(shopName)
                .shopAddress(shopAddress)
                .imageUrl(imageUrl)
                .imageUrlList(imageUrlList)
                .groomers(groomers)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
