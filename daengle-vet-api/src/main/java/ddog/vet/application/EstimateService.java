package ddog.vet.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.dto.request.VetCareEstimateReq;
import ddog.domain.estimate.dto.response.CareEstimateInfo;
import ddog.domain.estimate.dto.response.UserCareEstimateDetail;
import ddog.domain.vet.Vet;
import ddog.persistence.port.CareEstimatePersist;
import ddog.persistence.port.VetPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final VetPersist vetPersist;
    private final CareEstimatePersist careEstimatePersist;

    @Transactional(readOnly = true)
    public CareEstimateInfo findCareEstimateInfo(Long accountId) {
        Vet vet = vetPersist.getVetByAccountId(accountId);
        List<CareEstimate> generalEstimates = careEstimatePersist.findGeneralCareEstimates(vet.getAddress());
        List<CareEstimate> designationEstimates = careEstimatePersist.findDesignationCareEstimates(vet.getVetId());
        return CareEstimate.findCareEstimateInfo(generalEstimates, designationEstimates);
    }

    @Transactional(readOnly = true)
    public UserCareEstimateDetail getCareEstimateDetailInfo(Long careEstimateId) {
        CareEstimate careEstimate = careEstimatePersist.getByCareEstimateId(careEstimateId);
        return careEstimate.toUserCareEstimateDetail();
    }

    @Transactional
    public void createVetCareEstimate(VetCareEstimateReq request, Long accountId) {
        CareEstimate careEstimate = careEstimatePersist.getByCareEstimateId(request.getId());
        Vet vet = vetPersist.getVetByAccountId(accountId);
        careEstimatePersist.save(careEstimate.createVetCareEstimate(request, vet));
    }
}
