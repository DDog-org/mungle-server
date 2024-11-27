package ddog.daengleserver.presentation.dto.response;

import ddog.daengleserver.domain.Provider;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountInfoDto {
    private String email;
    private String nickname;
    private Provider provider;
}
