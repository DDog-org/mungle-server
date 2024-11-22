package ddog.daengleserver.application;

import ddog.daengleserver.application.repository.AccountRepository;
import ddog.daengleserver.domain.Account;
import ddog.daengleserver.global.auth.config.enums.Role;
import ddog.daengleserver.presentation.dto.response.AccountInfoDto;
import ddog.daengleserver.presentation.usecase.AccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountUseCase {

    private final AccountRepository accountRepository;

    public AccountInfoDto getAccountInfo(String email, Role role) {
        Account account = accountRepository.findAccountByEmailAndRole(email, role);
        return account.withAccount();
    }

    @Transactional
    public void updateNicknameById(long id, String nickname) {
        Account account = accountRepository.findBy(id);
        account = account.withNickname(nickname);
        accountRepository.save(account);
    }

}
