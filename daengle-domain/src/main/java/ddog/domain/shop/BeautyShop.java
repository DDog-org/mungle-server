package ddog.domain.shop;

import ddog.domain.groomer.Groomer;
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
public class BeautyShop {
    private Long shopId;
    private String shopName;
    private String shopAddress;
    private List<String> imageUrlList;
    private List<Groomer> groomers;
    private LocalTime startTime;
    private LocalTime endTime;
    private String introduction;
}
