package ddog.persistence.adapter;

import ddog.domain.user.User;
import ddog.persistence.jpa.entity.UserJpaEntity;
import ddog.persistence.jpa.repository.UserJpaRepository;
import ddog.persistence.port.UserPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserPersist {

    private final UserJpaRepository userJpaRepository;

    @Override
    public void save(User user) {
        userJpaRepository.save(UserJpaEntity.from(user));
    }

    @Override
    public User findByAccountId(Long accountId) {
        return userJpaRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("user not found"))
                .toModel();
    }

    @Override
    public Boolean hasNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }
}
