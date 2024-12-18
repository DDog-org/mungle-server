package ddog.groomer.presentation.account.dto;

import ddog.domain.groomer.GroomerSummaryInfo;
import ddog.domain.vet.Day;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class ShopDetailInfo {
    private Long shopId;
    private String shopName;
    private String shopAddress;
    private String shopDetailAddress;
    private List<String> imageUrlList;
    private List<GroomerSummaryInfo> groomers;
    private LocalTime startTime;
    private LocalTime endTime;
    private String introduction;
    private List<Day> closedDay;
    private String shopNumber;
}
