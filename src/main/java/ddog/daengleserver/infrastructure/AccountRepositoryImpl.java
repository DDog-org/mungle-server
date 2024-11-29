package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.AccountRepository;
import ddog.daengleserver.domain.account.Account;
import ddog.account.enums.Role;
import ddog.daengleserver.implementation.AccountException;
import ddog.daengleserver.implementation.enums.AccountExceptionType;
import ddog.daengleserver.infrastructure.po.AccountJpaEntity;
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
    public Account save(Account account) {
        return accountJpaRepository.save(AccountJpaEntity.from(account))
                .toModel();
    }

    @Override
    public Account findById(Long accountId) {
        return accountJpaRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(AccountExceptionType.NOT_FOUND_ACCOUNT))
                .toModel();
    }

    @Override
    public Account findAccountByEmailAndRole(String email, Role role) {
        return accountJpaRepository.findByEmailAndRole(email, role)
                .orElseThrow(() -> new AccountException(AccountExceptionType.NOT_FOUND_ACCOUNT))
                .toModel();
    }
}
