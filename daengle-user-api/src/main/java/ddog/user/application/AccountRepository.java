package ddog.user.application;

import ddog.account.Account;
import ddog.account.Role;

public interface AccountRepository {

    boolean checkExistsAccountBy(String email, Role role);

    Account save(Account account);

    Account findById(Long accountId);

    Account findAccountByEmailAndRole(String email, Role role);
}
