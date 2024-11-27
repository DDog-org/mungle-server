package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.account.Pet;

import java.util.List;

public interface PetRepository {
    List<Pet> findPetsById(Long accountId);

    Pet save(Pet pet);

    Pet findByPetId(Long petId);

    void deletePetById(Long petId);
}
