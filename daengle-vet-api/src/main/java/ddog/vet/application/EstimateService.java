package ddog.vet.application;

import ddog.domain.estimate.CareEstimate;
import ddog.vet.application.mapper.CareEstimateMapper;
import ddog.vet.presentation.estimate.dto.CareEstimateReq;
import ddog.vet.presentation.estimate.dto.CareEstimateInfo;
import ddog.vet.presentation.estimate.dto.CareEstimateDetail;
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
        return CareEstimateMapper.findCareEstimateInfo(generalEstimates, designationEstimates);
    }

    @Transactional(readOnly = true)
    public CareEstimateDetail getCareEstimateDetailInfo(Long careEstimateId) {
        CareEstimate careEstimate = careEstimatePersist.getByCareEstimateId(careEstimateId);
        return CareEstimateMapper.toUserCareEstimateDetail(careEstimate);
    }

    @Transactional
    public void createVetCareEstimate(CareEstimateReq request, Long accountId) {
        CareEstimate careEstimate = careEstimatePersist.getByCareEstimateId(request.getId());
        Vet vet = vetPersist.getVetByAccountId(accountId);
        careEstimatePersist.save(CareEstimateMapper.createVetCareEstimate(request, vet, careEstimate));
    }
}
