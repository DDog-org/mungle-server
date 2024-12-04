package ddog.persistence.port;

import ddog.domain.pet.Pet;

public interface PetPersist {
    Pet save(Pet pet);

    Pet findByPetId(Long accountId);

    void deletePetById(Long petId);
}
