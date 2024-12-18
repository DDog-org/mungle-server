package ddog.persistence.rdb.adapter;

import ddog.domain.pet.Pet;
import ddog.persistence.rdb.jpa.entity.PetJpaEntity;
import ddog.persistence.rdb.jpa.repository.PetJpaRepository;
import ddog.domain.pet.port.PetPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PetRepository implements PetPersist {

    private final PetJpaRepository petJpaRepository;

    @Override
    public Pet save(Pet pet) {
        return petJpaRepository.save(PetJpaEntity.from(pet))
                .toModel();
    }

    @Override
    public Optional<Pet> findByPetId(Long petId) {
        return petJpaRepository.findByPetId(petId)
                .map(PetJpaEntity::toModel);
    }

    @Override
    public void deletePetById(Long petId) {
        petJpaRepository.deleteByPetId(petId);
    }
}