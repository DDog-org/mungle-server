package ddog.mungleserver.domain;

import ddog.mungleserver.global.auth.config.enums.Provider;
import ddog.mungleserver.global.auth.config.enums.Role;
import lombok.*;

@Getter
@Builder
@RequiredArgsConstructor
public class Customer {

    private final Long id;
    private final String email;
    private final String nickname;
    private final Provider provider;
    private final Role role;

}
