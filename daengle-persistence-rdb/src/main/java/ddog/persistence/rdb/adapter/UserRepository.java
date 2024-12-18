package ddog.persistence.rdb.adapter;

import ddog.domain.user.User;
import ddog.persistence.rdb.jpa.entity.UserJpaEntity;
import ddog.persistence.rdb.jpa.repository.UserJpaRepository;
import ddog.domain.user.port.UserPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserPersist {

    private final UserJpaRepository userJpaRepository;

    @Override
    public void save(User user) {
        userJpaRepository.save(UserJpaEntity.from(user));
    }

    @Override
    public Optional<User> findByAccountId(Long accountId) {
        return userJpaRepository.findByAccountId(accountId)
                .map(UserJpaEntity::toModel);
    }

    @Override
    public Boolean hasNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }

    @Override
    public void deleteByAccountId(Long accountId) {
        userJpaRepository.deleteByAccountId(accountId);
    }
}
