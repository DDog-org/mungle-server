package ddog.daengleserver.presentation.usecase;

import ddog.daengleserver.domain.Role;
import ddog.daengleserver.presentation.dto.response.AccountInfoDto;

public interface AccountUseCase {

    AccountInfoDto getAccountInfo(String email, Role role);

    void updateNicknameById(long id, String nickname);
}
