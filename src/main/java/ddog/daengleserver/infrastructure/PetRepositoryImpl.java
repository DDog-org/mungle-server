package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.PetRepository;
import ddog.daengleserver.domain.account.Pet;
import ddog.daengleserver.implementation.AccountException;
import ddog.daengleserver.infrastructure.po.PetJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PetRepositoryImpl implements PetRepository {

    private final PetJpaRepository petJpaRepository;

    @Override
    public List<Pet> findPetsById(Long userId) {

        List<Pet> petList = new ArrayList<>();

        for (PetJpaEntity petJpaEntity : petJpaRepository.findByUserId(userId)) {
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