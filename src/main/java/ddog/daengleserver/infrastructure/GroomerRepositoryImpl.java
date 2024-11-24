package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.GroomerRepository;
import ddog.daengleserver.domain.Groomer;
import ddog.daengleserver.implementation.AccountException;
import ddog.daengleserver.implementation.enums.AccountExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroomerRepositoryImpl implements GroomerRepository {

    private final GroomerJpaRepository groomerJpaRepository;

    @Override
    public String getAddressById(Long accountId) {
        return groomerJpaRepository.findByGroomerId(accountId)
                .orElseThrow(() -> new AccountException(AccountExceptionType.NOT_FOUND_ACCOUNT))
                .getAddress();
    }

    @Override
    public Groomer getGroomerById(Long accountId) {
        return groomerJpaRepository.getByGroomerId(accountId)
                .toModel();
    }
}
