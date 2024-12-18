package ddog.domain.user.port;

import ddog.domain.user.User;

import java.util.Optional;

public interface UserPersist {
    void save(User user);

    Optional<User> findByAccountId(Long accountId);

    Boolean hasNickname(String nickname);

    void deleteByAccountId(Long accountId);
}
