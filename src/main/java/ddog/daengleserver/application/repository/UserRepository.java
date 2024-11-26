package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.User;

public interface UserRepository {

    void save(User user);

    User findById(Long accountId);
}
