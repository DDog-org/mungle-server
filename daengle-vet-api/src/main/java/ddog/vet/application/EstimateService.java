package ddog.vet.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.domain.vet.Vet;
import ddog.persistence.port.CareEstimatePersist;
import ddog.persistence.port.PetPersist;
import ddog.persistence.port.UserPersist;
import ddog.persistence.port.VetPersist;
import ddog.vet.application.mapper.CareEstimateMapper;
import ddog.vet.presentation.estimate.dto.EstimateDetail;
import ddog.vet.presentation.estimate.dto.EstimateInfo;
import ddog.vet.presentation.estimate.dto.EstimateReq;
import ddog.vet.presentation.estimate.dto.EstimateResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final VetPersist vetPersist;
    private final PetPersist petPersist;
    private final UserPersist userPersist;
    private final CareEstimatePersist careEstimatePersist;

    @Transactional(readOnly = true)
    public EstimateInfo findEstimateInfo(Long accountId) {
        Vet vet = vetPersist.getVetByAccountId(accountId);
        List<CareEstimate> generalEstimates = careEstimatePersist.findGeneralCareEstimates(vet.getAddress());
        List<CareEstimate> designationEstimates = careEstimatePersist.findDesignationCareEstimates(vet.getAccountId());

        List<EstimateInfo.Content> allContents = estimatesToContents(generalEstimates);
        List<EstimateInfo.Content> designationContents = estimatesToContents(designationEstimates);

        allContents.addAll(designationContents);

        // careEstimateId 기준으로 오름차순 정렬
        allContents.sort(Comparator.comparing(EstimateInfo.Content::getId));

        return EstimateInfo.builder()
                .allEstimates(allContents)
                .designationEstimates(designationContents)
                .build();
    }

    private List<EstimateInfo.Content> estimatesToContents(List<CareEstimate> estimates) {
        List<EstimateInfo.Content> contents = new ArrayList<>();
        for (CareEstimate estimate : estimates) {
            System.out.println(estimate.getUserId()+ " " + estimate.getPetId());
            User user = userPersist.findByAccountId(estimate.getUserId());
            Pet pet = petPersist.findByPetId(estimate.getPetId());

            contents.add(CareEstimateMapper.estimateToContent(estimate, user, pet));
        }
        return contents;
    }

    @Transactional(readOnly = true)
    public EstimateDetail getEstimateDetail(Long careEstimateId) {
        CareEstimate careEstimate = careEstimatePersist.getByCareEstimateId(careEstimateId);
        User user = userPersist.findByAccountId(careEstimate.getUserId());
        Pet pet = petPersist.findByPetId(careEstimate.getPetId());

        return CareEstimateMapper.toUserCareEstimateDetail(careEstimate, user, pet);
    }

    @Transactional
    public EstimateResp createEstimate(EstimateReq request, Long accountId) {
        CareEstimate.validateDiagnosis(request.getDiagnosis());
        CareEstimate.validateCause(request.getCause());
        CareEstimate.validateTreatment(request.getTreatment());

        CareEstimate careEstimate = careEstimatePersist.getByCareEstimateId(request.getId());
        Vet vet = vetPersist.getVetByAccountId(accountId);

        careEstimatePersist.save(CareEstimateMapper.createVetCareEstimate(request, vet, careEstimate));
        return EstimateResp.builder()
                .requestResult("대기 진료 견적서 등록 완료")
                .build();
    }
}
