package ddog.user.application;

import ddog.user.User;

public interface UserRepository {

    void save(User user);

    User findByAccountId(Long accountId);

    Boolean hasNickname(String nickname);
}
