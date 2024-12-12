package ddog.user.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.Proposal;
import ddog.domain.estimate.port.CareEstimatePersist;
import ddog.domain.estimate.port.GroomingEstimatePersist;
import ddog.domain.groomer.Groomer;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.pet.Pet;
import ddog.domain.pet.port.PetPersist;
import ddog.domain.user.User;
import ddog.domain.user.port.UserPersist;
import ddog.domain.vet.Vet;
import ddog.domain.vet.port.VetPersist;
import ddog.user.application.exception.account.*;
import ddog.user.application.exception.estimate.CareEstimateException;
import ddog.user.application.exception.estimate.CareEstimateExceptionType;
import ddog.user.application.exception.estimate.GroomingEstimateException;
import ddog.user.application.exception.estimate.GroomingEstimateExceptionType;
import ddog.user.application.mapper.CareEstimateMapper;
import ddog.user.application.mapper.GroomingEstimateMapper;
import ddog.user.presentation.estimate.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final UserPersist userPersist;
    private final PetPersist petPersist;
    private final GroomerPersist groomerPersist;
    private final VetPersist vetPersist;

    private final GroomingEstimatePersist groomingEstimatePersist;
    private final CareEstimatePersist careEstimatePersist;

    @Transactional(readOnly = true)
    public UserInfo.Grooming getGroomerAndUserInfo(Long groomerId, Long userId) {

        User user = userPersist.findByAccountId(userId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        if (groomerId == null) {
            return GroomingEstimateMapper.mapToUserInfo(user);
        }

        Groomer groomer = groomerPersist.findByAccountId(groomerId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        return GroomingEstimateMapper.mapToUserInfoWithGroomer(groomer, user);
    }

    @Transactional(readOnly = true)
    public UserInfo.Care getVetAndUserInfo(Long vetId, Long userId) {

        User user = userPersist.findByAccountId(userId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        if (vetId == null) {
            return CareEstimateMapper.mapToUserInfo(user);
        }

        Vet vet = vetPersist.findByAccountId(vetId)
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        return CareEstimateMapper.mapToUserInfoWithVet(vet, user);
    }

    @Transactional
    public EstimateResp createNewGroomingEstimate(CreateNewGroomingEstimateReq request, Long accountId) {
        User.validateAddress(request.getAddress());
        GroomingEstimate.validateRequirements(request.getRequirements());

        if (request.getGroomerId() == null) {
            GroomingEstimate newEstimate = GroomingEstimateMapper.createNewGeneralGroomingEstimate(request, accountId);
            GroomingEstimate savedEstimate = groomingEstimatePersist.save(newEstimate);

            return EstimateResp.builder()
                    .estimateId(savedEstimate.getEstimateId())
                    .requestResult("(일반)신규 미용 견적서 등록 완료")
                    .build();
        }

        GroomingEstimate newEstimate = GroomingEstimateMapper.createNewDesignationGroomingEstimate(request, accountId);
        GroomingEstimate savedEstimate = groomingEstimatePersist.save(newEstimate);

        return EstimateResp.builder()
                .estimateId(savedEstimate.getEstimateId())
                .requestResult("(지정)신규 미용 견적서 등록 완료")
                .build();
    }

    @Transactional
    public EstimateResp createNewCareEstimate(CreateNewCareEstimateReq request, Long accountId) {
        User.validateAddress(request.getAddress());
        CareEstimate.validateSymptoms(request.getSymptoms());
        CareEstimate.validateRequirements(request.getRequirements());

        if (request.getVetId() == null) {
            CareEstimate newEstimate = CareEstimateMapper.createNewGeneralCareEstimate(request, accountId);
            CareEstimate savedEstimate = careEstimatePersist.save(newEstimate);

            return EstimateResp.builder()
                    .estimateId(savedEstimate.getEstimateId())
                    .requestResult("(일반)신규 진료 견적서 등록 완료")
                    .build();
        }

        CareEstimate newEstimate = CareEstimateMapper.createNewDesignationCareEstimate(request, accountId);
        CareEstimate savedEstimate = careEstimatePersist.save(newEstimate);

        return EstimateResp.builder()
                .estimateId(savedEstimate.getEstimateId())
                .requestResult("(지정)신규 진료 견적서 등록 완료")
                .build();
    }

    @Transactional(readOnly = true)
    public EstimateInfo.Pet findGeneralGroomingPets(Long accountId) {
        User user = userPersist.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        List<Pet> pets = user.getPets();
        List<EstimateInfo.Pet.Content> contents = new ArrayList<>();
        for (Pet pet : pets) {
            groomingEstimatePersist.findByEstimateStatusAndProposalAndPetId(EstimateStatus.NEW, Proposal.GENERAL, pet.getPetId())
                    .ifPresent(estimate -> contents.add(EstimateInfo.Pet.Content.builder()
                            .estimateId(estimate.getEstimateId())
                            .petId(pet.getPetId())
                            .imageURL(pet.getImageURL())
                            .name(pet.getName())
                            .build()));
        }
        return EstimateInfo.Pet.builder()
                .pets(contents)
                .build();
    }

    public EstimateInfo.Pet findGeneralCarePets(Long accountId) {
        User user = userPersist.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        List<Pet> pets = user.getPets();
        List<EstimateInfo.Pet.Content> contents = new ArrayList<>();
        for (Pet pet : pets) {
            careEstimatePersist.findByEstimateStatusAndProposalAndPetId(EstimateStatus.NEW, Proposal.GENERAL, pet.getPetId())
                    .ifPresent(estimate -> contents.add(EstimateInfo.Pet.Content.builder()
                            .estimateId(estimate.getEstimateId())
                            .petId(pet.getPetId())
                            .imageURL(pet.getImageURL())
                            .name(pet.getName())
                            .build()));
        }
        return EstimateInfo.Pet.builder()
                .pets(contents)
                .build();
    }

    @Transactional(readOnly = true)
    public EstimateInfo.Grooming findGeneralGroomingEstimates(Long petId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<GroomingEstimate> estimates = groomingEstimatePersist.findByPetIdAndStatusAndProposal(petId, EstimateStatus.PENDING, Proposal.GENERAL, pageable);

        return groomingEstimatesToContents(estimates);
    }

    @Transactional(readOnly = true)
    public EstimateInfo.Care findGeneralCareEstimates(Long petId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CareEstimate> estimates = careEstimatePersist.findByPetIdAndStatusAndProposal(petId, EstimateStatus.PENDING, Proposal.GENERAL, pageable);

        return careEstimatesToContents(estimates);
    }

    @Transactional(readOnly = true)
    public EstimateInfo.Pet findDesignationGroomingPets(Long accountId) {
        User user = userPersist.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        List<Pet> pets = user.getPets();
        List<EstimateInfo.Pet.Content> contents = new ArrayList<>();
        for (Pet pet : pets) {
            groomingEstimatePersist.findByEstimateStatusAndProposalAndPetId(EstimateStatus.NEW, Proposal.DESIGNATION, pet.getPetId())
                    .ifPresent(estimate -> contents.add(EstimateInfo.Pet.Content.builder()
                            .estimateId(estimate.getEstimateId())
                            .petId(pet.getPetId())
                            .imageURL(pet.getImageURL())
                            .name(pet.getName())
                            .build()));
        }
        return EstimateInfo.Pet.builder()
                .pets(contents)
                .build();
    }

    @Transactional(readOnly = true)
    public EstimateInfo.Pet findDesignationCarePets(Long accountId) {
        User user = userPersist.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        List<Pet> pets = user.getPets();
        List<EstimateInfo.Pet.Content> contents = new ArrayList<>();
        for (Pet pet : pets) {
            careEstimatePersist.findByEstimateStatusAndProposalAndPetId(EstimateStatus.NEW, Proposal.DESIGNATION, pet.getPetId())
                    .ifPresent(estimate -> contents.add(EstimateInfo.Pet.Content.builder()
                            .estimateId(estimate.getEstimateId())
                            .petId(pet.getPetId())
                            .imageURL(pet.getImageURL())
                            .name(pet.getName())
                            .build()));
        }
        return EstimateInfo.Pet.builder()
                .pets(contents)
                .build();
    }

    @Transactional(readOnly = true)
    public EstimateInfo.Grooming findDesignationGroomingEstimates(Long petId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GroomingEstimate> estimates = groomingEstimatePersist.findByPetIdAndStatusAndProposal(petId, EstimateStatus.PENDING, Proposal.DESIGNATION, pageable);

        return groomingEstimatesToContents(estimates);
    }

    @Transactional(readOnly = true)
    public EstimateInfo.Care findDesignationCareEstimates(Long petId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CareEstimate> estimates = careEstimatePersist.findByPetIdAndStatusAndProposal(petId, EstimateStatus.PENDING, Proposal.DESIGNATION, pageable);

        return careEstimatesToContents(estimates);
    }

    private EstimateInfo.Grooming groomingEstimatesToContents(Page<GroomingEstimate> estimates) {
        List<EstimateInfo.Grooming.Content> contents = new ArrayList<>();
        for (GroomingEstimate estimate : estimates) {
            Groomer groomer = groomerPersist.findByAccountId(estimate.getGroomerId())
                    .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

            contents.add(GroomingEstimateMapper.mapToEstimateInfo(estimate, groomer));
        }

        return EstimateInfo.Grooming.builder()
                .estimates(contents)
                .build();
    }

    private EstimateInfo.Care careEstimatesToContents(Page<CareEstimate> estimates) {
        List<EstimateInfo.Care.Content> contents = new ArrayList<>();
        for (CareEstimate estimate : estimates) {
            Vet vet = vetPersist.findByAccountId(estimate.getVetId())
                    .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

            contents.add(CareEstimateMapper.mapToEstimateInfo(estimate, vet));
        }

        return EstimateInfo.Care.builder()
                .estimates(contents)
                .build();
    }

    public UserEstimate.Grooming getGroomingEstimate(Long estimateId) {
        GroomingEstimate estimate = groomingEstimatePersist.findByEstimateId(estimateId)
                .orElseThrow(() -> new GroomingEstimateException(GroomingEstimateExceptionType.GROOMING_ESTIMATE_NOT_FOUND));

        Pet pet = petPersist.findByPetId(estimate.getPetId())
                .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

        return GroomingEstimateMapper.mapToUserGroomingEstimate(estimate, pet);
    }

    public UserEstimate.Care getCareEstimate(Long estimateId) {
        CareEstimate estimate = careEstimatePersist.findByEstimateId(estimateId)
                .orElseThrow(() -> new CareEstimateException(CareEstimateExceptionType.CARE_ESTIMATE_NOT_FOUND));

        Pet pet = petPersist.findByPetId(estimate.getPetId())
                .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

        return CareEstimateMapper.mapToUserCareEstimate(estimate, pet);
    }

    public EstimateResp cancelGroomingEstimate(Long estimateId) {
        GroomingEstimate estimate = groomingEstimatePersist.findByEstimateId(estimateId)
                .orElseThrow(() -> new GroomingEstimateException(GroomingEstimateExceptionType.GROOMING_ESTIMATE_NOT_FOUND));

        groomingEstimatePersist.updateStatusWithParentId(EstimateStatus.END, estimate.getEstimateId());

        return EstimateResp.builder()
                .requestResult("미용 견적서가 성공적으로 취소되었습니다.")
                .build();
    }

    public EstimateResp cancelCareEstimate(Long estimateId) {
        CareEstimate estimate = careEstimatePersist.findByEstimateId(estimateId)
                .orElseThrow(() -> new GroomingEstimateException(GroomingEstimateExceptionType.GROOMING_ESTIMATE_NOT_FOUND));

        careEstimatePersist.updateStatusWithParentId(EstimateStatus.END, estimate.getEstimateId());

        return EstimateResp.builder()
                .requestResult("진료 견적서가 성공적으로 취소되었습니다.")
                .build();
    }

    @Transactional(readOnly = true)
    public GroomingEstimateDetail getGroomingEstimateDetail(Long groomingEstimateId) {
        GroomingEstimate groomingEstimate = groomingEstimatePersist.findByEstimateId(groomingEstimateId)
                .orElseThrow(() -> new GroomingEstimateException(GroomingEstimateExceptionType.GROOMING_ESTIMATE_NOT_FOUND));

        Groomer groomer = groomerPersist.findByAccountId(groomingEstimate.getGroomerId())
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        Pet pet = petPersist.findByPetId(groomingEstimate.getPetId())
                .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

        return GroomingEstimateMapper.mapToEstimateDetail(groomingEstimate, groomer, pet);
    }

    @Transactional(readOnly = true)
    public CareEstimateDetail getCareEstimateDetail(Long careEstimateId) {
        CareEstimate careEstimate = careEstimatePersist.findByEstimateId(careEstimateId)
                .orElseThrow(() -> new CareEstimateException(CareEstimateExceptionType.CARE_ESTIMATE_NOT_FOUND));

        Vet vet = vetPersist.findByAccountId(careEstimate.getVetId())
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        Pet pet = petPersist.findByPetId(careEstimate.getPetId())
                .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

        return CareEstimateMapper.mapToEstimateDetail(careEstimate, vet, pet);
    }
}
