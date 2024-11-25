package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.NotifyMessage;
import java.util.List;

public interface NotifyMessageRepository {

    List<NotifyMessage> findByUserId(Long userId);

    void save(NotifyMessage notifyMessage);

    NotifyMessage findById(Long notifyId);

    void delete(Long notifiyMesasgeId);

}
