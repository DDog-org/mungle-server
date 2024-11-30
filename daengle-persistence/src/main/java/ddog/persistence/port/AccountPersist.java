package ddog.persistence.port;

import ddog.domain.account.Account;
import ddog.domain.account.Role;

public interface AccountPersist {
    boolean checkExistsAccountBy(String email, Role role);
    Account save(Account account);
    Account findById(Long accountId);
    Account findAccountByEmailAndRole(String email, Role role);
}
