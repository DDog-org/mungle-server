package ddog.daengleserver.application;

import ddog.daengleserver.application.repository.CareEstimateRepository;
import ddog.daengleserver.application.repository.VetRepository;
import ddog.daengleserver.domain.Vet;
import ddog.daengleserver.domain.estimate.CareEstimate;
import ddog.daengleserver.presentation.estimate.dto.request.UserDesignationCareEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserGeneralCareEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.VetCareEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.response.UserCareEstimateDetails;
import ddog.daengleserver.presentation.estimate.dto.response.CareEstimateInfos;
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
    public void createUserGeneralCareEstimate(UserGeneralCareEstimateReq request, Long userId) {
        careEstimateRepository.save(CareEstimate.createUserGeneralCareEstimate(request, userId));
    }

    @Transactional
    public void createUserDesignationCareEstimate(UserDesignationCareEstimateReq request, Long userId) {
        careEstimateRepository.save(CareEstimate.createUserDesignationCareEstimate(request, userId));
    }

    @Transactional(readOnly = true)
    public CareEstimateInfos findCareEstimateInfos(Long vetId) {
        Vet vet = vetRepository.getVetById(vetId);
        List<CareEstimate> generalEstimates = careEstimateRepository.findGeneralCareEstimates(vet.getAddress());
        List<CareEstimate> designationEstimates = careEstimateRepository.findDesignationCareEstimates(vet.getVetId());
        return CareEstimate.findCareEstimateInfos(generalEstimates, designationEstimates);
    }

    @Transactional(readOnly = true)
    public UserCareEstimateDetails getCareEstimateDetailInfo(Long careEstimateId) {
        CareEstimate careEstimate = careEstimateRepository.getByCareEstimateId(careEstimateId);
        return careEstimate.withUserCareEstimate();
    }

    @Transactional
    public void createVetCareEstimate(VetCareEstimateReq request, Long accountId) {
        CareEstimate careEstimate = careEstimateRepository.getByCareEstimateId(request.getCareEstimateId());
        Vet vet = vetRepository.getVetById(accountId);
        careEstimateRepository.save(careEstimate.createVetGroomingEstimate(request, vet));
    }
}
