package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.UserRepository;
import ddog.daengleserver.domain.account.User;
import ddog.daengleserver.domain.enums.UserExceptionType;
import ddog.daengleserver.domain.exception.UserException;
import ddog.daengleserver.infrastructure.po.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public void save(User user) {
        userJpaRepository.save(UserJpaEntity.from(user));
    }

    @Override
    public User findByAccountId(Long accountId) {
        return userJpaRepository.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND))
                .toModel();
    }

    @Override
    public Boolean hasNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }
}
