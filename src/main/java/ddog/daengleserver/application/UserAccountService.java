package ddog.daengleserver.application;

import ddog.daengleserver.application.repository.AccountRepository;
import ddog.daengleserver.application.repository.PetRepository;
import ddog.daengleserver.application.repository.UserRepository;
import ddog.daengleserver.domain.account.Account;
import ddog.daengleserver.domain.account.Pet;
import ddog.daengleserver.domain.account.User;
import ddog.daengleserver.domain.account.enums.Breed;
import ddog.daengleserver.presentation.account.dto.request.*;
import ddog.daengleserver.presentation.account.dto.response.BreedInfos;
import ddog.daengleserver.presentation.account.dto.response.PetInfos;
import ddog.daengleserver.presentation.account.dto.response.UserProfileInfo;
import ddog.daengleserver.presentation.estimate.dto.response.UserAndPetsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    @Transactional(readOnly = true)
    public Boolean hasNickname(String nickname) {
        return !userRepository.hasNickname(nickname);
    }

    /* TODO PetService 추가하면 그곳으로 옮기기 */
    public BreedInfos getBreedInfos() {
        List<BreedInfos.Detail> details = new ArrayList<>();
        for (Breed breed : Breed.values()) {
            details.add(BreedInfos.Detail.builder()
                    .breed(breed.toString())
                    .breedName(breed.getName())
                    .build());
        }
        return BreedInfos.builder()
                .breedList(details)
                .build();
    }

    @Transactional
    public void createUserWithoutPet(JoinUserWithoutPet request) {
        Account accountToSave = Account.create(request.getEmail(), request.getRole());
        Account savedAccount = accountRepository.save(accountToSave);
        User user = User.createWithoutPet(savedAccount.getAccountId(), request);
        userRepository.save(user);
    }

    @Transactional
    public void createUserWithPet(JoinUserWithPet request) {
        Account accountToSave = Account.create(request.getEmail(), request.getRole());
        Account savedAccount = accountRepository.save(accountToSave);
        Pet pet = Pet.toJoinPetInfo(savedAccount.getAccountId(), request);
        User user = User.createWithPet(savedAccount.getAccountId(), request, pet);
        petRepository.save(pet);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserProfileInfo getUserProfileInfo(Long userId) {
        User user = userRepository.findByAccountId(userId);
        return user.toUserProfileInfo();
    }

    @Transactional
    public void modifyUserProfile(UserProfileModifyReq request, Long accountId) {
        User user = userRepository.findByAccountId(accountId);
        User modifiedUser = user.withImageAndNickname(request.getUserImage(), request.getNickname());
        userRepository.save(modifiedUser);
    }

    @Transactional
    public void addPet(AddPetInfo request, Long accountId) {
        User user = userRepository.findByAccountId(accountId);
        Pet newPet = Pet.create(accountId, request);
        Pet savedPet = petRepository.save(newPet);
        userRepository.save(user.withNewPet(savedPet));
    }

    @Transactional(readOnly = true)
    public PetInfos getPetInfos(Long accountId) {
        User user = userRepository.findByAccountId(accountId);
        return user.toPetInfos();
    }

    @Transactional
    public void modifyPetProfile(ModifyPetInfo request, Long accountId) {
        /* TODO 수정할 반려동물이 해당 사용자의 반려동물인지 유효성 검증 추가해야할듯 */
        Pet modifiedPet = Pet.withModifyPetInfo(request, accountId);
        petRepository.save(modifiedPet);
    }

    @Transactional(readOnly = true)
    public UserAndPetsInfo getUserAddressAndPetsInfoById(Long userId) {
        User user = userRepository.findByAccountId(userId);
        return user.findAddressAndPetsInfo();
    }

    @Transactional
    public void deletePet(Long petId) {
        /* TODO PetService 추가하면 그곳으로 옮기기 */
        petRepository.deletePetById(petId);
    }
}
