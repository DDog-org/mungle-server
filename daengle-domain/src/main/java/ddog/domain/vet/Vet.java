package ddog.domain.vet;

import ddog.domain.vet.enums.AreaCode;
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
    private String vetName;
    private String vetImage;
    private String address;
    private String detailAddress;
    private String phoneNumber;
    private String vetIntroduction;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<Day> closedDays;
    private List<String> licenses;

    public static void validateName(String name) {
        if (name == null || name.length() < 2 || name.length() > 10 || !name.matches("^[가-힣\\s]+$")) {
            throw new IllegalArgumentException("Invalid name: must be 2-10 characters and in Korean.");
        }
    }

    public static void validateAddress(String address) {
        if (address == null || !address.matches("^\\S+시 \\S+구 \\S+동$")) {
            throw new IllegalArgumentException("Invalid address: must follow the format '00시 00구 00동'.");
        }
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
}
