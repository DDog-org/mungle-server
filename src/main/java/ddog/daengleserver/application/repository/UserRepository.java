package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.account.User;

public interface UserRepository {

    void save(User user);

    User findByAccountId(Long accountId);

    Boolean hasNickname(String nickname);
}
