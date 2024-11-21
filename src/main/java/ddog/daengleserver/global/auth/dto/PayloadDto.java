package ddog.daengleserver.global.auth.dto;

import ddog.daengleserver.global.auth.config.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayloadDto {
    private String email;
    private Role role;
}
