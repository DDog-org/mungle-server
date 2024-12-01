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

}
