package ddog.user.application;

import ddog.account.Account;
import ddog.estimate.dto.response.UserInfo;
import ddog.pet.Breed;
import ddog.pet.Pet;
import ddog.user.User;
import ddog.user.dto.request.*;
import ddog.user.dto.response.BreedInfo;
import ddog.user.dto.response.PetInfo;
import ddog.user.dto.response.UserProfileInfo;
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
    public BreedInfo getBreedInfos() {
        List<BreedInfo.Detail> details = new ArrayList<>();
        for (Breed breed : Breed.values()) {
            details.add(BreedInfo.Detail.builder()
                    .breed(breed.toString())
                    .breedName(breed.getName())
                    .build());
        }
        return BreedInfo.builder()
                .breedList(details)
                .build();
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

    @Transactional
    public void createUserWithoutPet(JoinUserWithoutPet request) {
        Account accountToSave = Account.create(request.getEmail(), request.getRole());
        Account savedAccount = accountRepository.save(accountToSave);
        User user = User.createWithoutPet(savedAccount.getAccountId(), request);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserProfileInfo getUserProfileInfo(Long accountId) {
        User user = userRepository.findByAccountId(accountId);
        return user.toUserProfileInfo();
    }

    @Transactional
    public void modifyUserProfile(UserProfileModifyReq request, Long accountId) {
        User user = userRepository.findByAccountId(accountId);
        User modifiedUser = user.withImageAndNickname(request.getImage(), request.getNickname());
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
    public PetInfo getPetInfo(Long accountId) {
        User user = userRepository.findByAccountId(accountId);
        return user.toPetInfo();
    }

    @Transactional
    public void modifyPetProfile(ModifyPetInfo request, Long accountId) {
        /* TODO 수정할 반려견이 해당 사용자의 반려견인지 유효성 검증 추가해야할듯 */
        Pet modifiedPet = Pet.withModifyPetInfo(request, accountId);
        petRepository.save(modifiedPet);
    }

    @Transactional(readOnly = true)
    public UserInfo getUserAndPetInfos(Long userId) {
        User user = userRepository.findByAccountId(userId);
        return user.toUserInfo();
    }

    @Transactional
    public void deletePet(Long petId) {
        /* TODO PetService 추가하면 그곳으로 옮기기 */
        petRepository.deletePetById(petId);
    }
}
