package ddog.domain.groomer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Groomer {

    private Long groomerId;
    private Long accountId;
    private int daengleMeter;
    private String groomerName;
    private String phoneNumber;
    private String groomerImage;
    private String email;
    private String address;
    private String detailAddress;
    private String shopName;
    private String groomerIntroduction;
    private List<String> businessLicenses;
    private List<String> licenses;

    public static void validateShopName(String shopName) {
        if (shopName == null || !shopName.matches("^[가-힣a-zA-Z0-9][가-힣a-zA-Z0-9\\s]{0,19}$")) {
            throw new IllegalArgumentException("Shop name must be 1-20 characters long and can contain Korean, " +
                    "English letters, numbers, and spaces, but cannot start or end with a space.");
        }
    }

    public static void validateName(String name) {
        if (name == null || !name.matches("^[가-힣]{2,10}$")) {
            throw new IllegalArgumentException("Name must be 2-10 Korean characters.");
        }
    }

    public static void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("^010-\\d{4}-\\d{4}$")) {
            throw new IllegalArgumentException("Phone number must be in the format 010-0000-0000.");
        }
    }

    public static void validateAddress(String address) { //TODO 공공 데이터 추가 후 재작업
//        if (address == null || !address.matches("^[가-힣a-zA-Z\\s]+\\s[가-힣a-zA-Z\\s]+\\s[가-힣a-zA-Z\\s]+$")) {
//            throw new IllegalArgumentException("Address must be in the format 'City District Neighborhood' separated by spaces.");
//        }
    }

    public static void validateDetailAddress(String detailAddress) {
        if (detailAddress.length() > 20) {
            throw new IllegalArgumentException("Detail address must be 20 characters or less.");
        }
    }

    public static void validateBusinessLicenses(List<String> businessLicenses) {
        if (businessLicenses == null || businessLicenses.isEmpty() || businessLicenses.size() > 2) {
            throw new IllegalArgumentException("Business licenses must contain between 1 and 2 entries.");
        }
    }

    public static void validateLicenses(List<String> licenses) {
        if (licenses == null || licenses.isEmpty() || licenses.size() > 2) {
            throw new IllegalArgumentException("Licenses must contain between 1 and 2 entries.");
        }
    }

    public static void validateIntroduction(String introduction) {
        if (introduction != null && (introduction.isEmpty() || introduction.length() > 50)) {
            throw new IllegalArgumentException("Invalid introduction: must be 50 characters or less if provided.");
        }
    }
}
