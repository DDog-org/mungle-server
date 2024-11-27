package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.User;

public interface UserRepository {

    User findById(Long accountId);
}
