package ddog.domain.shop;

import ddog.domain.groomer.Groomer;
import ddog.domain.vet.Day;
import ddog.domain.vet.enums.AreaCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeautyShop {
    private Long shopId;
    private String shopName;
    private String shopAddress;
    private String shopDetailAddress;
    private String phoneNumber;
    private String imageUrl;
    private List<String> imageUrlList;
    private List<Groomer> groomers;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<Day> closedDays;
    private String introduction;

    public static void validateTimeRange(LocalTime startTime, LocalTime endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Invalid time: startTime and endTime cannot be null.");
        }
        if (startTime == endTime) {
            throw new IllegalArgumentException("Invalid time: startTime and endTime cannot be the same.");
        }
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("Invalid time range: endTime cannot be before startTime.");
        }
    }

    public static void validateImageUrlList(List<String> imageUrlList) {
        if (imageUrlList != null && imageUrlList.size() > 10) {
            throw new IllegalArgumentException("Invalid image url list: The maximum number of images is 10.");
        }
    }

    public static void validateClosedDays(List<Day> closedDays) {
        if (closedDays == null || closedDays.isEmpty()) {
            return; // null or empty list is valid
        }
        for (Day day : closedDays) {
            if (day == null) {
                throw new IllegalArgumentException("Invalid closed day: element cannot be null.");
            }
        }
    }

    public static void validateIntroduction(String introduction) {
        if (introduction == null || introduction.length() <= 50) {
            return; // valid case
        }
        throw new IllegalArgumentException("Invalid introduction: must be 50 characters or less.");
    }

    public static void validatePhoneNumber(String phoneNumber) {
        // 1. 기본 형식 확인
        if (phoneNumber == null || !phoneNumber.matches("^\\d{2,3}-\\d{3,4}-\\d{4}$")) {
            throw new IllegalArgumentException("Invalid phone number: must follow the format {area code}-{number}.");
        }

        // 2. 지역번호 확인
        String[] parts = phoneNumber.split("-");
        String areaCode = parts[0];

        boolean isInValidAreaCode = Arrays.stream(AreaCode.values())
                .noneMatch(code -> code.getCode().equals(areaCode));

        if (isInValidAreaCode) {
            throw new IllegalArgumentException("Invalid area code: " + areaCode + ". Must be a valid Korean area code.");
        }
    }

    public static BeautyShop create(String shopName, String shopAddress) {
        return BeautyShop.builder()
                .shopName(shopName)
                .shopAddress(shopAddress)
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(18, 0))
                .introduction(shopName)
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
