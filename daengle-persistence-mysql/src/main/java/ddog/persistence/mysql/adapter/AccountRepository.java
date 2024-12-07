package ddog.persistence.mysql.adapter;

import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.persistence.mysql.jpa.entity.AccountJpaEntity;
import ddog.persistence.mysql.jpa.repository.AccountJpaRepository;
import ddog.persistence.mysql.port.AccountPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
                .orElseThrow(() -> new RuntimeException("account not found"))
                .toModel();
    }

    @Override
    public Optional<Account> findAccountByEmailAndRole(String email, Role role) {
        return accountJpaRepository.findByEmailAndRole(email, role)
                .map(AccountJpaEntity::toModel);
    }
}
