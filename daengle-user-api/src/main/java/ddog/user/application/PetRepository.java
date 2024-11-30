package ddog.user.application;

import ddog.domain.pet.Pet;

public interface PetRepository {

    Pet save(Pet pet);

    void deletePetById(Long petId);
}
