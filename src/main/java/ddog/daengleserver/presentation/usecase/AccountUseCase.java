package ddog.daengleserver.presentation.usecase;

import ddog.daengleserver.global.auth.config.enums.Role;
import ddog.daengleserver.presentation.dto.response.AccountInfoDto;

public interface AccountUseCase {

    AccountInfoDto getAccountInfo(String email, Role role);

    void updateNicknameById(long id, String nickname);
}
