package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.Pet;

import java.util.List;

public interface PetRepository {
    List<Pet> findPetsById(Long accountId);

    void save(Pet pet);
}
