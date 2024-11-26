package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.GroomerRepository;
import ddog.daengleserver.domain.account.Groomer;
import ddog.daengleserver.domain.enums.UserExceptionType;
import ddog.daengleserver.domain.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroomerRepositoryImpl implements GroomerRepository {

    private final GroomerJpaRepository groomerJpaRepository;

    @Override
    public String getAddressById(Long accountId) {
        return groomerJpaRepository.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND))
                .getAddress();
    }

    @Override
    public Groomer getGroomerByAccountId(Long accountId) {
        return groomerJpaRepository.getByAccountId(accountId)
                .toModel();
    }
}
