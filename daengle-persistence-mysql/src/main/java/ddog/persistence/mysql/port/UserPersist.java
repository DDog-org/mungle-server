package ddog.persistence.mysql.port;

import ddog.domain.user.User;

public interface UserPersist {
    void save(User user);

    User findByAccountId(Long accountId);

    Boolean hasNickname(String nickname);
}
