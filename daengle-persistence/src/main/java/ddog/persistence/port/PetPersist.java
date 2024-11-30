package ddog.persistence.port;

import ddog.domain.pet.Pet;

public interface PetPersist {
    Pet save(Pet pet);
    void deletePetById(Long petId);
}
