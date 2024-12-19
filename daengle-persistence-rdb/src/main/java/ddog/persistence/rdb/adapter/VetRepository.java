package ddog.persistence.rdb.adapter;

import ddog.domain.vet.Vet;
import ddog.domain.vet.enums.CareBadge;
import ddog.persistence.rdb.jpa.entity.VetJpaEntity;
import ddog.persistence.rdb.jpa.repository.VetJpaRepository;
import ddog.domain.vet.port.VetPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class VetRepository implements VetPersist {

    private final VetJpaRepository vetJpaRepository;

    @Override
    public Optional<Vet> findByAccountId(Long accountId) {
        return vetJpaRepository.findByAccountId(accountId)
                .map(VetJpaEntity::toModel);
    }

    @Override
    public Optional<Vet> findByVetId(Long vetId) {
        return vetJpaRepository.findByVetId(vetId)
                .map(VetJpaEntity::toModel);
    }

    @Override
    public Vet save(Vet vet) {
        return vetJpaRepository.save(VetJpaEntity.from(vet)).toModel();
    }

    @Override
    public Page<Vet> findByAddress(String address, Pageable pageable) {
        Page<VetJpaEntity> vets = vetJpaRepository.findVetsByAddress(address, pageable);

        return vets.map(VetJpaEntity::toModel);
    }

    @Override
    public List<Vet> findByAddressPrefix(String addressPrefix) {
        List<VetJpaEntity> vets = vetJpaRepository.findVetsByAddressPrefix(addressPrefix);

        return vets.stream()
                .map(VetJpaEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Vet> findVetByKeyword(String address, String keyword, CareBadge tag, Pageable pageable) {
        return vetJpaRepository.findAllVetsBy(address, keyword, tag, pageable).map(VetJpaEntity::toModel);
    }


    @Override
    public void deleteByAccountId(Long accountId) {
        vetJpaRepository.deleteByAccountId(accountId);
    }
}
