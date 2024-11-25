package ddog.daengleserver.application;

import ddog.daengleserver.application.repository.CareEstimateRepository;
import ddog.daengleserver.application.repository.GroomingEstimateRepository;
import ddog.daengleserver.application.repository.UserRepository;
import ddog.daengleserver.domain.Pet;
import ddog.daengleserver.domain.User;
import ddog.daengleserver.domain.estimate.CareEstimate;
import ddog.daengleserver.domain.estimate.GroomingEstimate;
import ddog.daengleserver.presentation.estimate.dto.response.CareEstimateDetails;
import ddog.daengleserver.presentation.estimate.dto.response.EstimateInfo;
import ddog.daengleserver.presentation.estimate.dto.response.GroomingEstimateDetails;
import ddog.daengleserver.presentation.estimate.dto.response.UserAndPetsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GroomingEstimateRepository groomingEstimateRepository;
    private final CareEstimateRepository careEstimateRepository;

    @Transactional(readOnly = true)
    public UserAndPetsInfo getUserAddressAndPetsInfoById(Long accountId) {
        User user = userRepository.findById(accountId);
        return user.findAddressAndPetsInfo();
    }

    @Transactional(readOnly = true)
    public EstimateInfo findEstimateInfoById(Long accountId) {
        User user = userRepository.findById(accountId);
        List<Pet> pets = user.getPets();

        List<EstimateInfo.PetEstimate> petEstimates = new ArrayList<>();
        for (Pet pet : pets) {

            List<EstimateInfo.PetEstimate.GroomingEstimateInfo> groomingEstimateInfos = new ArrayList<>();
            List<EstimateInfo.PetEstimate.CareEstimateInfo> careEstimateInfos = new ArrayList<>();

            List<GroomingEstimate> groomingEstimates = groomingEstimateRepository.findGroomingEstimatesByPetId(pet.getPetId());
            for (GroomingEstimate groomingEstimate : groomingEstimates) {
                groomingEstimateInfos.add(EstimateInfo.PetEstimate.GroomingEstimateInfo.builder()
                        .groomingEstimateId(groomingEstimate.getGroomingEstimateId())
                        .groomerName(groomingEstimate.getGroomerName())
                        .groomerImage(groomingEstimate.getGroomerImage())
                        .shopName(groomingEstimate.getShopName())
                        .reservedDate(groomingEstimate.getReservedDate())
                        .build());
            }
            List<CareEstimate> careEstimates = careEstimateRepository.findCareEstimatesByPetId(pet.getPetId());
            for (CareEstimate careEstimate : careEstimates) {
                careEstimateInfos.add(EstimateInfo.PetEstimate.CareEstimateInfo.builder()
                        .careEstimateId(careEstimate.getCareEstimateId())
                        .vetName(careEstimate.getVetName())
                        .vetImage(careEstimate.getVetImage())
                        .reservedDate(careEstimate.getReservedDate())
                        .build());
            }

            petEstimates.add(EstimateInfo.PetEstimate.builder()
                    .petName(pet.getPetName())
                    .petImage(pet.getPetImage())
                    .groomingEstimates(groomingEstimateInfos)
                    .careEstimates(careEstimateInfos)
                    .build());
        }

        return EstimateInfo.builder()
                .petEstimates(petEstimates)
                .build();
    }

    @Transactional(readOnly = true)
    public GroomingEstimateDetails getGroomingEstimateDetails(Long groomingEstimateId) {
        GroomingEstimate groomingEstimate = groomingEstimateRepository.getByGroomingEstimateId(groomingEstimateId);
        return groomingEstimate.getGroomingEstimateDetails();
    }

    @Transactional(readOnly = true)
    public CareEstimateDetails getCareEstimateDetails(Long careEstimateId) {
        CareEstimate careEstimate = careEstimateRepository.getByCareEstimateId(careEstimateId);
        return careEstimate.getCareEstimateDetails();
    }
}
