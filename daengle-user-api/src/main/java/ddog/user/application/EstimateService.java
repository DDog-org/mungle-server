package ddog.user.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.GroomingEstimate;
import ddog.user.application.mapper.CareEstimateMapper;
import ddog.user.application.mapper.GroomingEstimateMapper;
import ddog.user.presentation.estimate.dto.DesignationCareEstimateReq;
import ddog.user.presentation.estimate.dto.DesignationGroomingEstimateReq;
import ddog.user.presentation.estimate.dto.GeneralCareEstimateReq;
import ddog.user.presentation.estimate.dto.GeneralGroomingEstimateReq;
import ddog.user.presentation.estimate.dto.CareEstimateDetail;
import ddog.user.presentation.estimate.dto.EstimateInfo;
import ddog.user.presentation.estimate.dto.GroomingEstimateDetail;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.persistence.port.CareEstimatePersist;
import ddog.persistence.port.GroomingEstimatePersist;
import ddog.persistence.port.UserPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final UserPersist userPersist;
    private final GroomingEstimatePersist groomingEstimatePersist;
    private final CareEstimatePersist careEstimatePersist;

    @Transactional
    public void createGeneralGroomingEstimate(GeneralGroomingEstimateReq request, Long accountId) {
        groomingEstimatePersist.save(GroomingEstimateMapper.createGeneralGroomingEstimate(request, accountId));
    }

    @Transactional
    public void createDesignationGroomingEstimate(DesignationGroomingEstimateReq request, Long accountId) {
        groomingEstimatePersist.save(GroomingEstimateMapper.createDesignationGroomingEstimate(request, accountId));
    }

    @Transactional
    public void createGeneralCareEstimate(GeneralCareEstimateReq request, Long accountId) {
        careEstimatePersist.save(CareEstimateMapper.createGeneralCareEstimate(request, accountId));
    }

    @Transactional
    public void createDesignationCareEstimate(DesignationCareEstimateReq request, Long accountId) {
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
}
