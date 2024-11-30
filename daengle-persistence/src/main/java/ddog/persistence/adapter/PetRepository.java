package ddog.persistence.adapter;

import ddog.domain.pet.Pet;
import ddog.persistence.jpa.entity.PetJpaEntity;
import ddog.persistence.jpa.repository.PetJpaRepository;
import ddog.persistence.port.PetPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public void deletePetById(Long petId) {
        petJpaRepository.deleteByPetId(petId);
    }
}