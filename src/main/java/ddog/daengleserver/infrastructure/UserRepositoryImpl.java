package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.UserRepository;
import ddog.daengleserver.domain.User;
import ddog.daengleserver.domain.enums.UserExceptionType;
import ddog.daengleserver.domain.exception.UserException;
import ddog.daengleserver.implementation.AccountException;
import ddog.daengleserver.implementation.enums.AccountExceptionType;
import ddog.daengleserver.infrastructure.po.AccountJpaEntity;
import ddog.daengleserver.infrastructure.po.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public void save(User user) {
        AccountJpaEntity accountJpaEntity = accountJpaRepository.findById(user.getAccountId())
                .orElseThrow(() -> new AccountException(AccountExceptionType.NOT_FOUND_ACCOUNT));
        userJpaRepository.save(UserJpaEntity.from(user, accountJpaEntity));
    }

    @Override
    public User findById(Long accountId) {
        return userJpaRepository.findById(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND))
                .toModel();
    }

    @Override
    public Boolean hasNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }
}
