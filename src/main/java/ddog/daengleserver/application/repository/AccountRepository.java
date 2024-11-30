package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.account.Account;
import ddog.daengleserver.domain.account.enums.Role;

public interface AccountRepository {

    boolean checkExistsAccountBy(String email, Role role);

    Account save(Account account);

    Account findById(Long accountId);

    Account findAccountByEmailAndRole(String email, Role role);
}
