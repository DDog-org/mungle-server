package ddog.persistence.mysql.port;

import ddog.domain.account.Account;
import ddog.domain.account.Role;

import java.util.Optional;

public interface AccountPersist {
    boolean checkExistsAccountBy(String email, Role role);
    Account save(Account account);
    Account findById(Long accountId);
    Optional<Account> findAccountByEmailAndRole(String email, Role role);
}
