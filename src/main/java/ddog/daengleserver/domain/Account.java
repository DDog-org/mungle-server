package ddog.daengleserver.domain;

import ddog.daengleserver.presentation.dto.response.AccountInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Account {

    private final Long accountId;
    private final String email;
    private final String nickname;
    private final Provider provider;
    private final Role role;

    public Account withNickname(String nickname) {
        return Account.builder()
                .accountId(accountId)
                .email(email)
                .nickname(nickname)
                .provider(provider)
                .role(role)
                .build();
    }

    public AccountInfoDto withAccount() {
        return AccountInfoDto.builder()
                .email(email)
                .nickname(nickname)
                .provider(provider)
                .build();
    }
}
