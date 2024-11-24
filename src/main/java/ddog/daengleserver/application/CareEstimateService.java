package ddog.daengleserver.application;

import ddog.daengleserver.application.repository.CareEstimateRepository;
import ddog.daengleserver.application.repository.VetRepository;
import ddog.daengleserver.domain.Vet;
import ddog.daengleserver.domain.estimate.CareEstimate;
import ddog.daengleserver.presentation.estimate.dto.request.UserDesignationCareEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserGeneralCareEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.VetCareEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.response.UserCareEstimateDetails;
import ddog.daengleserver.presentation.estimate.dto.response.UserCareEstimateInfo;
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
    public void createUserGeneralCareEstimate(UserGeneralCareEstimateReq request) {
        careEstimateRepository.save(CareEstimate.createUserGeneralCareEstimate(request));
    }

    @Transactional
    public void createUserDesignationCareEstimate(UserDesignationCareEstimateReq request) {
        careEstimateRepository.save(CareEstimate.createUserDesignationCareEstimate(request));
    }

    @Transactional(readOnly = true)
    public List<UserCareEstimateInfo> findCareEstimateInfos(Long accountId) {
        String address = vetRepository.getAddressById(accountId);
        List<CareEstimate> careEstimates = careEstimateRepository.findCareEstimatesByAddress(address);
        return CareEstimate.withUserCareEstimates(careEstimates);
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
