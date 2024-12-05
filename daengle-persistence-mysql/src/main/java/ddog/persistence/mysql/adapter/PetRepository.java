package ddog.persistence.mysql.adapter;

import ddog.domain.pet.Pet;
import ddog.persistence.mysql.jpa.entity.PetJpaEntity;
import ddog.persistence.mysql.jpa.repository.PetJpaRepository;
import ddog.persistence.mysql.port.PetPersist;
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
    public Pet findByPetId(Long petId) {
        return petJpaRepository.findPetByPetId(petId)
                .orElseThrow(() -> new RuntimeException("pet not found"))
                .toModel();
    }

    @Override
    public void deletePetById(Long petId) {
        petJpaRepository.deleteByPetId(petId);
    }
}