package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.NotifyMessageRepository;
import ddog.daengleserver.domain.NotifyMessage;
import ddog.daengleserver.infrastructure.po.NotifyMessageJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotifyMessageRepositoryImpl implements NotifyMessageRepository  {

    private final NotifyMessageJpaRepository notifyMessageJpaRepository;
    @Override
    public List<NotifyMessage> findByUserId(Long userId) {
        return Optional.ofNullable(notifyMessageJpaRepository.findByUserId(userId))
                .filter(list -> list.isEmpty())
                .orElseThrow(() -> new RuntimeException("No Notification Message"))
                .stream()
                .map(NotifyMessageJpaEntity::toModel)
                .toList();
    }

    @Override
    public void save(NotifyMessage notifyMessage) {
        notifyMessageJpaRepository.save(NotifyMessageJpaEntity.from(notifyMessage));
    }

    @Override
    public NotifyMessage findById(Long notifyId) {
        return notifyMessageJpaRepository.findById(notifyId)
                .orElseThrow(() -> new RuntimeException("No Notification Id"))
                .toModel();
    }

    @Override
    public void delete(Long notifiyMesasgeId) {
        notifyMessageJpaRepository.deleteById(notifiyMesasgeId);
    }
}
