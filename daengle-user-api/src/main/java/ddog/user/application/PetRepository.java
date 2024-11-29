package ddog.user.application;

import ddog.pet.Pet;

import java.util.List;

public interface PetRepository {
    List<Pet> findPetsById(Long accountId);

    Pet save(Pet pet);

    Pet findByPetId(Long petId);

    void deletePetById(Long petId);
}
