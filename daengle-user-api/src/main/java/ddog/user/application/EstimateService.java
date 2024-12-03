package ddog.user.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.groomer.Groomer;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.domain.vet.Vet;
import ddog.persistence.port.*;
import ddog.user.application.mapper.CareEstimateMapper;
import ddog.user.application.mapper.GroomingEstimateMapper;
import ddog.user.presentation.estimate.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final UserPersist userPersist;
    private final GroomerPersist groomerPersist;
    private final VetPersist vetPersist;
    private final GroomingEstimatePersist groomingEstimatePersist;
    private final CareEstimatePersist careEstimatePersist;

    @Transactional
    public EstimateResp.Grooming createGroomingEstimate(GroomingEstimateReq request, Long accountId) {
        User.validateAddress(request.getAddress());

        if (request.getGroomerId() == null) {

            GroomingEstimate newEstimate = GroomingEstimateMapper.createGeneralGroomingEstimate(request, accountId);
            groomingEstimatePersist.save(newEstimate);

            return EstimateResp.Grooming.builder()
                    .requestResult("(일반)사용자 미용 견적서 등록 완료")
                    .build();
        }

        GroomingEstimate newEstimate = GroomingEstimateMapper.createDesignationGroomingEstimate(request, accountId);
        groomingEstimatePersist.save(newEstimate);
        return EstimateResp.Grooming.builder()
                .requestResult("(지정)사용자 미용 견적서 등록 완료")
                .build();
    }

    @Transactional
    public void createCareEstimate(CareEstimateReq request, Long accountId) {
        if (request.getVetId() == null) {
            careEstimatePersist.save(CareEstimateMapper.createGeneralCareEstimate(request, accountId));
            return;
        }
        careEstimatePersist.save(CareEstimateMapper.createDesignationCareEstimate(request, accountId));
    }

    @Transactional(readOnly = true)
    public EstimateInfo findEstimateInfo(Long accountId) {
        User user = userPersist.findByAccountId(accountId);
        List<Pet> pets = user.getPets();

        List<EstimateInfo.PetInfo> petInfos = new ArrayList<>();
        for (Pet pet : pets) {
            List<GroomingEstimate> groomingEstimates = groomingEstimatePersist.findGroomingEstimatesByPetId(pet.getPetId());
            List<EstimateInfo.PetInfo.Grooming> groomingInfos = GroomingEstimateMapper.toInfos(groomingEstimates);

            List<CareEstimate> careEstimates = careEstimatePersist.findCareEstimatesByPetId(pet.getPetId());
            List<EstimateInfo.PetInfo.Care> careInfos = CareEstimateMapper.toInfos(careEstimates);

            petInfos.add(EstimateInfo.PetInfo.builder()
                    .petId(pet.getPetId())
                    .name(pet.getPetName())
                    .image(pet.getPetImage())
                    .groomingEstimates(groomingInfos)
                    .careEstimates(careInfos)
                    .build());
        }
        return EstimateInfo.builder()
                .petInfos(petInfos)
                .build();
    }

    @Transactional(readOnly = true)
    public GroomingEstimateDetail getGroomingEstimateDetail(Long groomingEstimateId) {
        GroomingEstimate groomingEstimate = groomingEstimatePersist.getByGroomingEstimateId(groomingEstimateId);
        return GroomingEstimateMapper.getGroomingEstimateDetail(groomingEstimate);
    }

    @Transactional(readOnly = true)
    public CareEstimateDetail getCareEstimateDetail(Long careEstimateId) {
        CareEstimate careEstimate = careEstimatePersist.getByCareEstimateId(careEstimateId);
        return CareEstimateMapper.getCareEstimateDetail(careEstimate);
    }

    @Transactional(readOnly = true)
    public AccountInfo.Grooming getGroomerAndUserInfo(Long groomerId, Long userId) {
        Groomer groomer = groomerPersist.getGroomerByAccountId(groomerId);
        User user = userPersist.findByAccountId(userId);

        return GroomingEstimateMapper.toGroomingInfo(groomer, user);
    }

    @Transactional(readOnly = true)
    public AccountInfo.Care getVetAndUserInfo(Long vetId, Long userId) {
        Vet vet = vetPersist.getVetByAccountId(vetId);
        User user = userPersist.findByAccountId(userId);

        return GroomingEstimateMapper.toCareInfo(vet, user);
    }
}
