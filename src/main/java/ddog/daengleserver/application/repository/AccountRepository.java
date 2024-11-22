package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.Account;
import ddog.daengleserver.domain.Role;

public interface AccountRepository {

    boolean checkExistsAccountBy(String email, Role role);

    void save(Account account);

    Account findBy(long id);

    Account findAccountByEmailAndRole(String email, Role role);
}
