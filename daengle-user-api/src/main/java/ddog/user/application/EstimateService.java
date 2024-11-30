package ddog.user.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.dto.request.UserDesignationCareEstimateReq;
import ddog.domain.estimate.dto.request.UserDesignationGroomingEstimateReq;
import ddog.domain.estimate.dto.request.UserGeneralCareEstimateReq;
import ddog.domain.estimate.dto.request.UserGeneralGroomingEstimateReq;
import ddog.domain.estimate.dto.response.CareEstimateDetail;
import ddog.domain.estimate.dto.response.EstimateInfo;
import ddog.domain.estimate.dto.response.GroomingEstimateDetail;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final UserRepository userRepository;
    private final GroomingEstimateRepository groomingEstimateRepository;
    private final CareEstimateRepository careEstimateRepository;

    @Transactional
    public void createUserGeneralGroomingEstimate(UserGeneralGroomingEstimateReq request, Long accountId) {
        groomingEstimateRepository.save(GroomingEstimate.createUserGeneralGroomingEstimate(request, accountId));
    }

    @Transactional
    public void createUserDesignationGroomingEstimate(UserDesignationGroomingEstimateReq request, Long accountId) {
        groomingEstimateRepository.save(GroomingEstimate.createUserDesignationGroomingEstimate(request, accountId));
    }

    @Transactional
    public void createUserGeneralCareEstimate(UserGeneralCareEstimateReq request, Long accountId) {
        careEstimateRepository.save(CareEstimate.createUserGeneralCareEstimate(request, accountId));
    }

    @Transactional
    public void createUserDesignationCareEstimate(UserDesignationCareEstimateReq request, Long accountId) {
        careEstimateRepository.save(CareEstimate.createUserDesignationCareEstimate(request, accountId));
    }

    @Transactional(readOnly = true)
    public EstimateInfo findEstimateInfo(Long accountId) {
        User user = userRepository.findByAccountId(accountId);
        List<Pet> pets = user.getPets();

        List<EstimateInfo.PetInfo> petInfos = new ArrayList<>();
        for (Pet pet : pets) {
            List<GroomingEstimate> groomingEstimates = groomingEstimateRepository.findGroomingEstimatesByPetId(pet.getPetId());
            List<EstimateInfo.PetInfo.Grooming> groomingInfos = GroomingEstimate.toInfos(groomingEstimates);

            List<CareEstimate> careEstimates = careEstimateRepository.findCareEstimatesByPetId(pet.getPetId());
            List<EstimateInfo.PetInfo.Care> careInfos = CareEstimate.toInfos(careEstimates);

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
        GroomingEstimate groomingEstimate = groomingEstimateRepository.getByGroomingEstimateId(groomingEstimateId);
        return groomingEstimate.getGroomingEstimateDetail();
    }

    @Transactional(readOnly = true)
    public CareEstimateDetail getCareEstimateDetail(Long careEstimateId) {
        CareEstimate careEstimate = careEstimateRepository.getByCareEstimateId(careEstimateId);
        return careEstimate.getCareEstimateDetail();
    }
}
