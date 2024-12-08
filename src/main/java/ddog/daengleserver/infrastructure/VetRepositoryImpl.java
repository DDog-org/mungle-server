package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.VetRepository;
import ddog.daengleserver.domain.account.Vet;
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
        return vetJpaRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AccountException(AccountExceptionType.NOT_FOUND_ACCOUNT))
                .getAddress();
    }

    @Override
    public Vet getVetByAccountId(Long accountId) {
        return vetJpaRepository.getByAccountId(accountId)
                .toModel();
    }

    @Override
    public Vet getVetByVetId(Long vetId) {
        return vetJpaRepository.findByVetId(vetId).get().toModel();
    }
}
