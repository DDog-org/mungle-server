package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.VetRepository;
import ddog.daengleserver.domain.Vet;
import ddog.daengleserver.implementation.AccountException;
import ddog.daengleserver.implementation.enums.AccountExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VetRepositoryImpl implements VetRepository {

    private final VetJpaRepository vetJpaRepository;

    @Override
    public String getAddressById(Long accountId) {
        return vetJpaRepository.findByVetId(accountId)
                .orElseThrow(() -> new AccountException(AccountExceptionType.NOT_FOUND_ACCOUNT))
                .getAddress();
    }

    @Override
    public Vet getVetById(Long accountId) {
        return vetJpaRepository.getByVetId(accountId)
                .toModel();
    }
}
