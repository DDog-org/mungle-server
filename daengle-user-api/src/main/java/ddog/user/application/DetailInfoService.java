package ddog.user.application;

import ddog.domain.groomer.Groomer;
import ddog.domain.groomer.GroomerSummaryInfo;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.review.CareReview;
import ddog.domain.review.GroomingReview;
import ddog.domain.review.port.CareReviewPersist;
import ddog.domain.review.port.GroomingReviewPersist;
import ddog.domain.shop.BeautyShop;
import ddog.domain.shop.port.BeautyShopPersist;
import ddog.domain.user.User;
import ddog.domain.user.port.UserPersist;
import ddog.domain.vet.Vet;
import ddog.domain.vet.port.VetPersist;
import ddog.user.application.exception.account.*;
import ddog.user.application.mapper.DetailInfoMapper;
import ddog.user.presentation.detailInfo.dto.DetailResp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetailInfoService {
    private final BeautyShopPersist beautyShopPersist;
    private final UserPersist userPersist;
    private final CareReviewPersist careReviewPersist;
    private final VetPersist vetPersist;
    private final GroomerPersist groomerPersist;
    private final GroomingReviewPersist groomingReviewPersist;

    public DetailResp findBeautyShops(Long accountId, String address, int page, int size) {
        address = checkUserLoggedIn(accountId, address);
        address = address.replace(" ", "");
        User savedUser = userPersist.findByAccountId(accountId).orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
        Pageable pageable = PageRequest.of(page, size);

        Page<BeautyShop> savedBeautyShop = beautyShopPersist.findBeautyShopsByAddress(address, pageable);

        List<DetailResp.ShopInfo> shopInfos = savedBeautyShop.stream().map(DetailInfoMapper::mapToBeautyShop).collect(Collectors.toList());

        return DetailResp.builder()
                .allShops(shopInfos)
                .totalPages(savedBeautyShop.getTotalPages())
                .totalElements(savedBeautyShop.getTotalElements())
                .currentPage(savedBeautyShop.getNumber())
                .userAddress(savedUser.getAddress())
                .build();
    }

    public DetailResp.ShopDetailInfo findBeautyShop(Long shopId) {
        BeautyShop findBeautyShop = beautyShopPersist.findBeautyShopById(shopId);
        List<GroomerSummaryInfo> groomerSummaryInfos = new ArrayList<>();

        for (Groomer groomer : findBeautyShop.getGroomers()) {
            Long groomerReviewCount = groomingReviewPersist.findByGroomerId(groomer.getGroomerId(), Pageable.unpaged()).getTotalElements();
            groomerSummaryInfos.add(GroomerSummaryInfo.builder()
                    .groomerName(groomer.getName())
                    .groomerImage(groomer.getImageUrl())
                    .reviewCount(groomerReviewCount)
                    .badges(groomer.getBadges())
                    .daengleMeter(groomer.getDaengleMeter())
                    .groomerAccountId(groomer.getAccountId())
                    .build());
        }

        return DetailResp.ShopDetailInfo.builder()
                .shopId(findBeautyShop.getShopId())
                .shopName(findBeautyShop.getShopName())
                .shopAddress(findBeautyShop.getShopAddress())
                .endTime(findBeautyShop.getEndTime())
                .startTime(findBeautyShop.getStartTime())
                .groomers(groomerSummaryInfos)
                .imageUrlList(findBeautyShop.getImageUrlList())
                .introduction(findBeautyShop.getIntroduction())
                .closedDay(findBeautyShop.getClosedDays())
                .shopNumber(findBeautyShop.getPhoneNumber())
                .build();
    }

    public DetailResp findVets(Long accountId, String address, int page, int size) {
        address = checkUserLoggedIn(accountId, address);

        address = address.replace(" ", "");

        User savedUser = userPersist.findByAccountId(accountId).orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
        Pageable pageable = PageRequest.of(page, size);
        Page<Vet> savedVet = vetPersist.findByAddress(address, pageable);

        List<DetailResp.VetInfo> vetInfos = savedVet.stream().map(DetailInfoMapper::mapToVet).collect(Collectors.toList());

        return DetailResp.builder()
                .allVets(vetInfos)
                .totalPages(savedVet.getTotalPages())
                .totalElements(savedVet.getTotalElements())
                .currentPage(savedVet.getNumber())
                .userAddress(savedUser.getAddress())
                .build();
    }

    public DetailResp.VetDetailInfo findVetById(Long vetAccountId) {
        Vet findVet = vetPersist.findByAccountId(vetAccountId).orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));
        Pageable pageable = Pageable.unpaged();
        Page<CareReview> results = careReviewPersist.findByVetId(findVet.getVetId(), pageable);

        return DetailResp.VetDetailInfo.builder()
                .vetAccountId(findVet.getAccountId())
                .vetImage(findVet.getImageUrl())
                .vetAddress(findVet.getAddress())
                .vetName(findVet.getName())
                .endTime(findVet.getEndTime())
                .startTime(findVet.getStartTime())
                .badges(findVet.getBadges())
                .introduction(findVet.getIntroduction())
                .daengleMeter(findVet.getDaengleMeter())
                .reviewCount(results.getTotalElements())
                .imageUrlList(findVet.getImageUrlList())
                .vetNumber(findVet.getPhoneNumber())
                .closedDay(findVet.getClosedDays())
                .build();
    }

    public DetailResp.GroomerDetailInfo findGroomerById(Long groomerAccountId) {
        Groomer findGroomer = groomerPersist.findByAccountId(groomerAccountId).orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));
        BeautyShop beautyShop = beautyShopPersist.findBeautyShopByNameAndAddress(findGroomer.getShopName(), findGroomer.getAddress()).get();
        Pageable pageable = Pageable.unpaged();

        Page<GroomingReview> groomingReview = groomingReviewPersist.findByGroomerId(findGroomer.getGroomerId(), pageable);

        return DetailResp.GroomerDetailInfo.builder()
                .groomerAccountId(findGroomer.getAccountId())
                .groomerName(findGroomer.getName())
                .daengleMeter(findGroomer.getDaengleMeter())
                .introduction(findGroomer.getIntroduction())
                .shopName(findGroomer.getShopName())
                .shopId(beautyShop.getShopId())
                .badges(findGroomer.getBadges())
                .reviewCount(groomingReview.getTotalElements())
                .groomerImage(findGroomer.getImageUrl())
                .instagram(findGroomer.getInstagramId())
                .build();
    }

    private String extractDistrict(String address) {
        if (address == null) return null;
        String[] parts = address.split(" ");
        System.out.println(parts.length);
        if (parts.length == 3) {
            return String.join(" ", parts[0], parts[1]);
        } else if (parts.length >= 4) {
            return String.join(" ", parts[0], parts[1], parts[2]);
        }
        return address;
    }

    private String checkUserLoggedIn(Long accountId, String address) {
        if (accountId == null) address = "서울 강남구 역삼동";
        else if (address == null) address = userPersist.findByAccountId(accountId).get().getAddress();
        return address;
    }

}
