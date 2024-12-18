package ddog.persistence.mysql.adapter;

import ddog.domain.groomer.Groomer;
import ddog.domain.groomer.enums.GroomingKeyword;
import ddog.persistence.mysql.jpa.entity.GroomerJpaEntity;
import ddog.persistence.mysql.jpa.repository.GroomerJpaRepository;
import ddog.domain.groomer.port.GroomerPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroomerRepository implements GroomerPersist {

    private final GroomerJpaRepository groomerJpaRepository;

    @Override
    public Optional<Groomer> findByAccountId(Long accountId) {
        return groomerJpaRepository.findByAccountId(accountId)
                .map(GroomerJpaEntity::toModel);
    }

    @Override
    public Groomer save(Groomer newGroomer) {
        return groomerJpaRepository.save(GroomerJpaEntity.from(newGroomer)).toModel();
    }

    @Override
    public Optional<Groomer> findByGroomerId(Long groomerId) {
        return groomerJpaRepository.findByGroomerId(groomerId)
                .map(GroomerJpaEntity::toModel);
    }

    @Override
    public Page<Groomer> findGroomerByKeyword(String address, String keyword, GroomingKeyword tag, Pageable pageable) {
        return groomerJpaRepository.findAllGroomersBy(address, keyword, tag, pageable).map(GroomerJpaEntity::toModel);
    }

    @Override
    public void deleteByAccountId(Long accountId) {
        groomerJpaRepository.deleteByAccountId(accountId);
    }
}
