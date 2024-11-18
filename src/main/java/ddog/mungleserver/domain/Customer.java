package ddog.mungleserver.domain;

import ddog.mungleserver.global.auth.config.enums.Provider;
import ddog.mungleserver.global.auth.config.enums.Role;
import lombok.*;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Customer {

    private Long id;
    private String email;
    private String nickname;
    private Provider provider;
    private Role role;

}
