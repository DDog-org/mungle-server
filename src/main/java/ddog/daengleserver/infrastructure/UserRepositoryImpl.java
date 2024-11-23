package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.UserRepository;
import ddog.daengleserver.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User findById(Long accountId) {
        return userJpaRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("user not found"))
                .toModel();
    }
}
