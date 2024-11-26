package ddog.daengleserver.domain.account;

import ddog.daengleserver.domain.account.enums.Provider;
import ddog.daengleserver.domain.account.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Account {

    private Long accountId;
    private String email;
    private Provider provider;
    private Role role;

    public static Account create(String email, Role role) {
        return Account.builder()
                .email(email)
                .provider(Provider.KAKAO)
                .role(role)
                .build();
    }

}
