package ddog.persistence.mysql.adapter;

import ddog.domain.user.User;
import ddog.persistence.mysql.jpa.entity.UserJpaEntity;
import ddog.persistence.mysql.jpa.repository.UserJpaRepository;
import ddog.persistence.mysql.port.UserPersist;
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
