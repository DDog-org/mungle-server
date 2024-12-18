package ddog.groomer.application;

import ddog.auth.config.jwt.JwtTokenProvider;
import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.domain.account.port.AccountPersist;
import ddog.domain.groomer.Groomer;
import ddog.domain.groomer.GroomerDaengleMeter;
import ddog.domain.groomer.GroomerSummaryInfo;
import ddog.domain.groomer.License;
import ddog.domain.groomer.port.GroomerDaengleMeterPersist;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.groomer.port.LicensePersist;
import ddog.domain.payment.Reservation;
import ddog.domain.payment.enums.ReservationStatus;
import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.shop.BeautyShop;
import ddog.domain.shop.port.BeautyShopPersist;
import ddog.groomer.application.exception.account.AccountException;
import ddog.groomer.application.exception.account.AccountExceptionType;
import ddog.groomer.application.exception.account.GroomerException;
import ddog.groomer.application.exception.account.GroomerExceptionType;
import ddog.groomer.application.mapper.BeautyShopMapper;
import ddog.groomer.application.mapper.GroomerDaengleMeterMapper;
import ddog.groomer.application.mapper.GroomerMapper;
import ddog.groomer.presentation.account.dto.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountPersist accountPersist;
    private final GroomerPersist groomerPersist;
    private final ReservationPersist reservationPersist;

    private final LicensePersist licensePersist;
    private final BeautyShopPersist beautyShopPersist;
    private final GroomerDaengleMeterPersist groomerDaengleMeterPersist;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResp signUp(SignUpReq request, HttpServletResponse response) {
        validateSignUpReqDataFormat(request);

        if (accountPersist.hasAccountByEmailAndRole(request.getEmail(), Role.GROOMER)) {
            throw new AccountException(AccountExceptionType.DUPLICATE_ACCOUNT);
        }

        Account newAccount = Account.create(request.getEmail(), Role.GROOMER);
        Account savedAccount = accountPersist.save(newAccount);

        List<License> licenses = new ArrayList<>();
        for (String imageUrl : request.getLicenses()) {
            License newLicense = License.createWithImageUrl(savedAccount.getAccountId(), imageUrl);
            License savedLicense = licensePersist.save(newLicense);

            licenses.add(savedLicense);
        }

        Authentication authentication = getAuthentication(savedAccount.getAccountId(), request.getEmail());
        String accessToken = jwtTokenProvider.generateToken(authentication, response);

        Optional<BeautyShop> existingBeautyShop = beautyShopPersist.findBeautyShopByNameAndAddress(request.getShopName(), request.getAddress());

        BeautyShop savedBeautyShop = existingBeautyShop.orElseGet(() -> BeautyShop.create(request.getShopName(), request.getAddress()));

        beautyShopPersist.save(savedBeautyShop);
        Long shopId = beautyShopPersist.findBeautyShopByNameAndAddress(savedBeautyShop.getShopName(), savedBeautyShop.getShopAddress()).get().getShopId();

        Groomer newGroomer = GroomerMapper.create(savedAccount.getAccountId(), request, licenses, shopId);
        Groomer savedGroomer = groomerPersist.save(newGroomer);

        GroomerDaengleMeter newGroomerDaengleMeter = GroomerDaengleMeterMapper.create(savedGroomer.getGroomerId());
        groomerDaengleMeterPersist.save(newGroomerDaengleMeter);

        return SignUpResp.builder()
                .accessToken(accessToken)
                .build();
    }

    @Transactional(readOnly = true)
    public ProfileInfo getGroomerInfo(Long accountId) {
        Groomer groomer = groomerPersist.findByAccountId(accountId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        return GroomerMapper.mapToProfileInfo(groomer);
    }

    @Transactional(readOnly = true)
    public ProfileInfo.UpdatePage getUpdatePage(Long accountId) {
        Groomer groomer = groomerPersist.findByAccountId(accountId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        List<License> licenses = groomer.getLicenses();
        List<ProfileInfo.UpdatePage.LicenseDetail> details = new ArrayList<>();
        for (License license : licenses) {
            details.add(ProfileInfo.UpdatePage.LicenseDetail.builder()
                    .name(license.getName())
                    .acquisitionDate(license.getAcquisitionDate())
                    .build());
        }

        return GroomerMapper.mapToUpdatePage(groomer, details);
    }

    @Transactional
    public AccountResp updateInfo(UpdateInfoReq request, Long accountId) {
        Groomer.validateIntroduction(request.getIntroduction());
        Groomer.validateInstagramId(request.getInstagramId());

        Groomer groomer = groomerPersist.findByAccountId(accountId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        Groomer updatedGroomer = GroomerMapper.updateWithUpdateInfoReq(groomer, request);
        groomerPersist.save(updatedGroomer);

        return AccountResp.builder()
                .requestResult("미용사 정보가 성공적으로 수정 되었습니다.")
                .build();
    }

    @Transactional(readOnly = true)
    public ShopInfo.UpdatePage getShopInfo(Long accountId) {
        Groomer groomer = groomerPersist.findByAccountId(accountId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        BeautyShop shop = beautyShopPersist.findBeautyShopById(groomer.getShopId());

        return BeautyShopMapper.mapToShopInfo(shop);
    }

    @Transactional
    public ShopInfo.UpdateResp updateShopInfo(UpdateShopReq request) {
        validateUpdateShopInfoDataFormat(request);

        BeautyShop shop = beautyShopPersist.findBeautyShopById(request.getShopId());
        BeautyShop updatedShop = BeautyShopMapper.updateShopInfoWithUpdateShopReq(shop, request);

        beautyShopPersist.save(updatedShop);

        return ShopInfo.UpdateResp.builder()
                .requestResp("마이샵 정보가 성공적으로 변경되었습니다.")
                .build();
    }

    @Transactional(readOnly = true)
    public WithdrawInfoResp getWithdrawInfo(Long accountId) {
        Groomer savedGroomer = groomerPersist.findByAccountId(accountId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        Optional<List<Reservation>> reservations = reservationPersist.findByRecipientIdAndReservationStatus(savedGroomer.getGroomerId(), ReservationStatus.DEPOSIT_PAID);
        Integer count = reservations.map(List::size).orElse(0);

        return WithdrawInfoResp.builder()
                .waitingForServiceCount(count)
                .build();
    }

    @Transactional
    public WithdrawResp withdraw(Long accountId) {  //TODO 탈퇴 후 추가로 처리해야할 작업들 고려하기 (ex. 예약취소)
        Groomer savedGroomer = groomerPersist.findByAccountId(accountId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        groomerPersist.deleteByAccountId(savedGroomer.getAccountId());

        return WithdrawResp.builder()
                .accountId(savedGroomer.getAccountId())
                .withdrawDate(LocalDateTime.now())
                .build();
    }

    @Transactional(readOnly = true)
    public ShopDetailInfo findBeautyShop(Long shopId) {

        BeautyShop findBeautyShop = beautyShopPersist.findBeautyShopById(shopId);

        List<GroomerSummaryInfo> groomerSummaryInfos = new ArrayList<>();
        for (Groomer groomer : findBeautyShop.getGroomers()) {
            groomerSummaryInfos.add(GroomerSummaryInfo.builder()
                    .groomerName(groomer.getName())
                    .groomerImage(groomer.getImageUrl())
                    .badges(groomer.getBadges())
                    .daengleMeter(groomer.getDaengleMeter())
                    .groomerAccountId(groomer.getAccountId())
                    .build());
        }

        return ShopDetailInfo.builder()
                .shopId(findBeautyShop.getShopId())
                .shopName(findBeautyShop.getShopName())
                .shopAddress(findBeautyShop.getShopAddress())
                .shopDetailAddress(findBeautyShop.getShopDetailAddress())
                .imageUrlList(findBeautyShop.getImageUrlList())
                .groomers(groomerSummaryInfos)
                .startTime(findBeautyShop.getStartTime())
                .endTime(findBeautyShop.getEndTime())
                .introduction(findBeautyShop.getIntroduction())
                .closedDay(findBeautyShop.getClosedDays())
                .shopNumber(findBeautyShop.getPhoneNumber())
                .build();
    }

    private void validateUpdateShopInfoDataFormat(UpdateShopReq request) {
        BeautyShop.validateImageUrlList(request.getImageUrlList());
        BeautyShop.validateTimeRange(request.getStartTime(), request.getEndTime());
        BeautyShop.validateClosedDays(request.getClosedDays());
        BeautyShop.validateIntroduction(request.getIntroduction());
        BeautyShop.validatePhoneNumber(request.getPhoneNumber());
    }

    private void validateSignUpReqDataFormat(SignUpReq request) {
        Groomer.validateShopName(request.getShopName());
        Groomer.validateName(request.getName());
        Groomer.validatePhoneNumber(request.getPhoneNumber());
        Groomer.validateAddress(request.getAddress());
        Groomer.validateDetailAddress(request.getDetailAddress());
        Groomer.validateBusinessLicenses(request.getBusinessLicenses());
        Groomer.validateLicenses(request.getLicenses());
    }

    private Authentication getAuthentication(Long accountId, String email) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_GROOMER"));

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(email + "," + accountId, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
