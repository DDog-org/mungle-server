package ddog.domain.account;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Account {

    private Long accountId;
    private String email;
    private Provider provider;
    private Role role;
    private Status status;

    public static Account createUser(String email, Role role) {
        return Account.builder()
                .email(email)
                .provider(Provider.KAKAO)
                .role(role)
                .status(Status.APPROVED)
                .build();
    }

    public static Account create(String email, Role role) {
        return Account.builder()
                .email(email)
                .provider(Provider.KAKAO)
                .role(role)
                .status(Status.PENDING)
                .build();
    }

    public static void validateUsername(String username) {
        if (username == null || username.length() < 2 || username.length() > 10) {
            throw new IllegalArgumentException("Invalid username: must be 2-10 characters.");
        }
    }

    public static void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("^010-\\d{4}-\\d{4}$")) {
            throw new IllegalArgumentException("Invalid phone number: must follow format 010-0000-0000.");
        }
    }

    public static void validateNickname(String nickname) {
        if (nickname == null || nickname.length() < 2 || nickname.length() > 10) {
            throw new IllegalArgumentException("Invalid nickname: must be 2-10 characters.");
        }
    }

    public static void validateAddress(String address) {
        if (address == null || !address.matches("^\\S+시 \\S+구 \\S+동$")) {
            throw new IllegalArgumentException("Invalid address: must follow format '00시 00구 00동'.");
        }
    }/**/
}
