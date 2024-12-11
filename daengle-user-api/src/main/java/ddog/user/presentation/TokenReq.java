package ddog.user.presentation;

import ddog.domain.account.Role;
import lombok.Getter;

@Getter
public class TokenReq {
    private String email;
    private Role role;
    private Long accountId;
}
