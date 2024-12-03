package ddog.persistence.port;

import ddog.domain.pet.Pet;

public interface PetPersist {
    Pet save(Pet pet);

    Pet findByAccountId(Long accountId);

    void deletePetById(Long petId);
}
