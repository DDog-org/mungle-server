package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.PetRepository;
import ddog.daengleserver.domain.Pet;
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
    public void save(Pet pet) {
        petJpaRepository.save(PetJpaEntity.from(pet));
    }
}
