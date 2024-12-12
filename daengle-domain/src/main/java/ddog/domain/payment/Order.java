package ddog.domain.payment;

import ddog.domain.payment.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long orderId;
    private ServiceType serviceType;
    private Long price;
    private Long estimateId;
    private String orderUid;
    private Long accountId;
    private String customerName;
    private Long recipientId;
    private String recipientImageUrl;
    private String recipientName;
    private String shopName;
    private LocalDateTime orderDate;
    private LocalDateTime schedule;
    private String visitorName;
    private String customerPhoneNumber;
    private String visitorPhoneNumber;
    private Payment payment;

    public static void validateRecipientName(String username) {
        if (username == null || username.length() < 2 || username.length() > 10) {
            throw new IllegalArgumentException("Invalid username: must be 2-10 characters.");
        }
    }

    public static void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("^010-\\d{4}-\\d{4}$")) {
            throw new IllegalArgumentException("Invalid phone number: must follow format 010-0000-0000.");
        }
    }

    public static void validateShopName(String shopName) {
        if (shopName == null || !shopName.matches("^[가-힣a-zA-Z0-9][가-힣a-zA-Z0-9\\s]{0,19}$")) {
            throw new IllegalArgumentException("Shop name must be 1-20 characters long and can contain Korean, " +
                    "English letters, numbers, and spaces, but cannot start or end with a space.");
        }
    }

    public static void validateSchedule(LocalDateTime schedule) {
        if (schedule == null || schedule.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Schedule cannot be in the past or NULL.");
        }
    }

    public static void validatePrice(Long price) {
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price must be a positive number.");
        }
    }
}
