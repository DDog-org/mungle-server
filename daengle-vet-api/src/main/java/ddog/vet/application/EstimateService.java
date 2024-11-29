package ddog.vet.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.dto.request.VetCareEstimateReq;
import ddog.domain.estimate.dto.response.CareEstimateInfo;
import ddog.domain.estimate.dto.response.UserCareEstimateDetail;
import ddog.domain.vet.Vet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final VetRepository vetRepository;
    private final CareEstimateRepository careEstimateRepository;

    @Transactional(readOnly = true)
    public CareEstimateInfo findCareEstimateInfo(Long accountId) {
        Vet vet = vetRepository.getVetByAccountId(accountId);
        List<CareEstimate> generalEstimates = careEstimateRepository.findGeneralCareEstimates(vet.getAddress());
        List<CareEstimate> designationEstimates = careEstimateRepository.findDesignationCareEstimates(vet.getVetId());
        return CareEstimate.findCareEstimateInfo(generalEstimates, designationEstimates);
    }

    @Transactional(readOnly = true)
    public UserCareEstimateDetail getCareEstimateDetailInfo(Long careEstimateId) {
        CareEstimate careEstimate = careEstimateRepository.getByCareEstimateId(careEstimateId);
        return careEstimate.toUserCareEstimateDetail();
    }

    @Transactional
    public void createVetCareEstimate(VetCareEstimateReq request, Long accountId) {
        CareEstimate careEstimate = careEstimateRepository.getByCareEstimateId(request.getId());
        Vet vet = vetRepository.getVetByAccountId(accountId);
        careEstimateRepository.save(careEstimate.createVetCareEstimate(request, vet));
    }
}
