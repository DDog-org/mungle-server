package ddog.daengleserver.global.auth.dto;

import ddog.daengleserver.domain.account.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayloadDto {
    private Long accountId;
    private String email;
    private Role role;
}
