package ddog.persistence.mysql.port;

import ddog.domain.user.User;

import java.util.Optional;

public interface UserPersist {
    void save(User user);

    User findByAccountId(Long accountId);

    Optional<User> findBy(Long id);

    Boolean hasNickname(String nickname);
}
