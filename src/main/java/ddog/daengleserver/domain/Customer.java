package ddog.daengleserver.domain;

import ddog.daengleserver.global.auth.config.enums.Provider;
import ddog.daengleserver.global.auth.config.enums.Role;
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
