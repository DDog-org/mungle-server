package ddog.domain.vet;

import ddog.domain.vet.enums.AreaCode;
import ddog.domain.vet.enums.CareKeyword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vet {

    private Long vetId;
    private Long accountId;
    private String email;
    private int daengleMeter;
    private String name;
    private String imageUrl;
    private List<String> imageUrlList;
    private String address;
    private String detailAddress;
    private String phoneNumber;
    private String introduction;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<Day> closedDays;
    private List<String> licenses;
    private List<CareKeyword> keywords;

    public void updateDaengleMeter(Integer newMeterValue) {
        this.daengleMeter = newMeterValue;
    }

    public static void validateName(String name) {
        if (name == null || name.length() < 2 || name.length() > 10 || !name.matches("^[가-힣\\s]+$")) {
            throw new IllegalArgumentException("Invalid name: must be 2-10 characters and in Korean.");
        }
    }

    public static void validateImageUrlList(List<String> imageUrlList) {
        if (imageUrlList != null && imageUrlList.size() > 10) {
            throw new IllegalArgumentException("Invalid image url list: The maximum number of images is 10.");
        }
    }

    public static void validateAddress(String address) {  //TODO 공공데이터 추가 후 재작업
//        if (address == null || !address.matches("^\\S+시 \\S+구 \\S+동$")) {
//            throw new IllegalArgumentException("Invalid address: must follow the format '00시 00구 00동'.");
//        }
    }

    public static void validateDetailAddress(String detailAddress) {
        if (detailAddress == null || detailAddress.length() > 20) {
            throw new IllegalArgumentException("Invalid detail address: must be up to 20 characters.");
        }
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

    public static void validateLicenses(List<String> licenses) { //현재 별다른 예외 처리 없음
    }

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
}
