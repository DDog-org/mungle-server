package ddog.daengleserver.application;

import ddog.daengleserver.application.repository.CareEstimateRepository;
import ddog.daengleserver.application.repository.VetRepository;
import ddog.daengleserver.domain.account.Vet;
import ddog.daengleserver.domain.estimate.CareEstimate;
import ddog.daengleserver.presentation.estimate.dto.request.UserDesignationCareEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserGeneralCareEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.VetCareEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.response.UserCareEstimateDetail;
import ddog.daengleserver.presentation.estimate.dto.response.CareEstimateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareEstimateService {

    private final VetRepository vetRepository;
    private final CareEstimateRepository careEstimateRepository;

    @Transactional
    public void createUserGeneralCareEstimate(UserGeneralCareEstimateReq request, Long accountId) {
        careEstimateRepository.save(CareEstimate.createUserGeneralCareEstimate(request, accountId));
    }

    @Transactional
    public void createUserDesignationCareEstimate(UserDesignationCareEstimateReq request, Long accountId) {
        careEstimateRepository.save(CareEstimate.createUserDesignationCareEstimate(request, accountId));
    }

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
    public void createVetCareEstimate(VetCareEstimateReq request, Long vetId) {
        CareEstimate careEstimate = careEstimateRepository.getByCareEstimateId(request.getCareEstimateId());
        Vet vet = vetRepository.getVetByAccountId(vetId);
        careEstimateRepository.save(careEstimate.createVetCareEstimate(request, vet));
    }
}
