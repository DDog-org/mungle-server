package ddog.user.application;

import ddog.domain.account.Account;
import ddog.domain.account.Role;

public interface AccountRepository {

    boolean checkExistsAccountBy(String email, Role role);

    Account save(Account account);

    Account findById(Long accountId);

    Account findAccountByEmailAndRole(String email, Role role);
}
