package ddog.user.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.port.CareEstimatePersist;
import ddog.domain.estimate.port.GroomingEstimatePersist;
import ddog.domain.pet.Pet;
import ddog.domain.review.CareReview;
import ddog.domain.review.GroomingReview;
import ddog.domain.review.port.CareReviewPersist;
import ddog.domain.review.port.GroomingReviewPersist;
import ddog.domain.user.User;
import ddog.domain.user.port.UserPersist;
import ddog.user.application.exception.account.UserException;
import ddog.user.application.exception.account.UserExceptionType;
import ddog.user.presentation.account.dto.MyProfileResp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountInfoService {
    private final UserPersist userPersist;
    private final GroomingReviewPersist groomingReviewPersist;
    private final CareReviewPersist careReviewPersist;
    private final GroomingEstimatePersist groomingEstimatePersist;
    private final CareEstimatePersist careEstimatePersist;

    public MyProfileResp findAccountInfo(Long accountId) {
        User savedUser = userPersist.findByAccountId(accountId).orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        String profileImage = savedUser.getImageUrl();
        String profileName = savedUser.getNickname();

        List<MyProfileResp.PetSummaryInfo> toSavePetInfo = new ArrayList<>();
        for (Pet savedPet : savedUser.getPets()) {
            toSavePetInfo.add(MyProfileResp.PetSummaryInfo.builder()
                    .petId(savedPet.getPetId())
                    .petImage(savedPet.getImageUrl())
                    .petName(savedPet.getName())
                    .build());
        }

        Page<GroomingReview> savedGroomingReview = groomingReviewPersist.findByReviewerId(accountId, Pageable.unpaged());
        Page<CareReview> savedCareReview = careReviewPersist.findByReviewerId(accountId, Pageable.unpaged());
        Integer reviewCount = Math.toIntExact(savedCareReview.getTotalElements() + savedGroomingReview.getTotalElements());

        List<GroomingEstimate> savedGroomingEstimate = groomingEstimatePersist.findByGroomingEstimateStatusBy(accountId, EstimateStatus.NEW);
        List<CareEstimate> savedCareEstimate = careEstimatePersist.findByCareEstimateStatusBy(accountId, EstimateStatus.NEW);
        Integer estimateCount = savedCareEstimate.size() + savedGroomingEstimate.size();

        return MyProfileResp.builder()
                .id(accountId)
                .image(profileImage)
                .nickname(profileName)
                .reviewCount(reviewCount)
                .estimateCount(estimateCount)
                .petInfos(toSavePetInfo)
                .build();

    }
}
