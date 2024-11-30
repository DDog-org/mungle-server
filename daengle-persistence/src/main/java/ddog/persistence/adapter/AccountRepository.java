package ddog.persistence.adapter;

import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.persistence.jpa.entity.AccountJpaEntity;
import ddog.persistence.jpa.repository.AccountJpaRepository;
import ddog.persistence.port.AccountPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountRepository implements AccountPersist {

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
