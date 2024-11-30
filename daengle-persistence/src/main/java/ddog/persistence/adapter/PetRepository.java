package ddog.persistence.adapter;

import ddog.domain.pet.Pet;
import ddog.persistence.jpa.entity.PetJpaEntity;
import ddog.persistence.jpa.repository.PetJpaRepository;
import ddog.persistence.port.PetPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PetRepository implements PetPersist {

    private final PetJpaRepository petJpaRepository;

    @Override
    public List<Pet> findPetsById(Long accountId) {

        List<Pet> petList = new ArrayList<>();

        for (PetJpaEntity petJpaEntity : petJpaRepository.findByAccountId(accountId)) {
            petList.add(petJpaEntity.toModel());
        }
        return petList;
    }

    @Override
    public Pet save(Pet pet) {
        return petJpaRepository.save(PetJpaEntity.from(pet))
                .toModel();
    }

    @Override
    public Pet findByPetId(Long petId) {
        return petJpaRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("pet not found"))
                .toModel();
    }

    @Override
    public void deletePetById(Long petId) {
        petJpaRepository.deleteByPetId(petId);
    }
}