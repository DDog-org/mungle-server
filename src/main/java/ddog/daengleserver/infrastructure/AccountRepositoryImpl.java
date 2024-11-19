package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.AccountRepository;
import ddog.daengleserver.domain.Account;
import ddog.daengleserver.global.auth.config.enums.Role;
import ddog.daengleserver.infrastructure.jpa.AccountJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    public boolean checkExistsAccountBy(String email, Role role) {
        return accountJpaRepository.existsByEmailAndRole(email, role);
    }

    @Override
    public void save(Account account) {
        accountJpaRepository.save(AccountJpaEntity.from(account));
    }
}
