package ddog.domain.payment;

import ddog.domain.payment.enums.ReservationStatus;
import ddog.domain.payment.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private Long reservationId;
    private Long estimateId;
    private Long petId;
    private ServiceType serviceType;
    private ReservationStatus reservationStatus;
    private Long recipientId;
    private String recipientImageUrl;
    private String recipientName;
    private String shopName;
    private LocalDateTime schedule;
    private Long deposit;
    private Long customerId;
    private String customerName;
    private String customerPhoneNumber;
    private String visitorName;
    private String visitorPhoneNumber;
    private Long paymentId;
    private Long petId;

    public static void validateShopName(String shopName) {
        if (shopName == null || !shopName.matches("^[가-힣a-zA-Z0-9][가-힣a-zA-Z0-9\\s]{0,19}$")) {
            throw new IllegalArgumentException("Shop name must be 1-20 characters long and can contain Korean, " +
                    "English letters, numbers, and spaces, but cannot start or end with a space.");
        }
    }

    public static void validateSchedule(LocalDateTime schedule) {
        if (schedule == null || schedule.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid schedule: must be a future date and time.");
        }
    }

    public static void validateDeposit(Long deposit) {
        if (deposit == null || deposit < 0) {
            throw new IllegalArgumentException("Invalid deposit: must be a non-negative value.");
        }
    }

    public static void validateName(String name) {
        if (name == null || name.length() < 2 || name.length() > 10 || !name.matches("^[가-힣\\s]+$")) {
            throw new IllegalArgumentException("Invalid name: must be 2-10 characters and in Korean.");
        }
    }

    public static void validatePhoneNumber(String phoneNumber) {
        // 1. 기본 형식 확인
        if (phoneNumber == null || !phoneNumber.matches("^\\d{2,3}-\\d{3,4}-\\d{4}$")) {
            throw new IllegalArgumentException("Invalid phone number: must follow the format {area code}-{number}.");
        }
    }
}
