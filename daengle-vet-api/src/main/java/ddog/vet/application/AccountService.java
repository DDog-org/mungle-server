package ddog.vet.application;

import ddog.auth.config.jwt.JwtTokenProvider;
import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.domain.payment.Reservation;
import ddog.domain.payment.enums.ReservationStatus;
import ddog.domain.payment.port.ReservationPersist;
import ddog.domain.vet.Vet;
import ddog.domain.account.port.AccountPersist;
import ddog.domain.vet.VetDaengleMeter;
import ddog.domain.vet.port.VetDaengleMeterPersist;
import ddog.domain.vet.port.VetPersist;
import ddog.vet.application.exception.account.VetException;
import ddog.vet.application.exception.account.VetExceptionType;
import ddog.vet.application.mapper.VetDaengleMeterMapper;
import ddog.vet.application.mapper.VetMapper;
import ddog.vet.presentation.account.dto.*;
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
    private final ReservationPersist reservationPersist;

    private final VetPersist vetPersist;
    private final VetDaengleMeterPersist vetDaengleMeterPersist;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResp signUp(SignUpReq request, HttpServletResponse response) {
        validateSignUpRequestDataFormat(request);

        Account newAccount = Account.create(request.getEmail(), Role.VET);
        Account savedAccount = accountPersist.save(newAccount);

        Vet newVet = VetMapper.create(savedAccount.getAccountId(), request);
        Vet savedVet = vetPersist.save(newVet);

        VetDaengleMeter vetDaengleMeterToSave = VetDaengleMeterMapper.create(savedVet.getVetId());
        vetDaengleMeterPersist.save(vetDaengleMeterToSave);

        Authentication authentication = getAuthentication(savedAccount.getAccountId(), request.getEmail());
        String accessToken = jwtTokenProvider.generateToken(authentication, response);

        return SignUpResp.builder()
                .accessToken(accessToken)
                .build();
    }

    private void validateSignUpRequestDataFormat(SignUpReq request) {
        Vet.validateName(request.getName());
        Vet.validateAddress(request.getAddress());
        Vet.validateDetailAddress(request.getDetailAddress());
        Vet.validatePhoneNumber(request.getPhoneNumber());
        Vet.validateLicenses(request.getLicenses());
    }

    private Authentication getAuthentication(Long accountId, String email) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_VET"));

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(email + "," + accountId, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public ProfileInfo getVetInfo(Long accountId) {
        Vet vet = vetPersist.findByAccountId(accountId)
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        return VetMapper.mapToProfileInfo(vet);
    }

    @Transactional(readOnly = true)
    public ProfileInfo.UpdatePage getModifyPage(Long accountId) {
        Vet vet = vetPersist.findByAccountId(accountId)
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        return VetMapper.mapToUpdatePage(vet);
    }

    @Transactional
    public AccountResp modifyInfo(UpdateInfo request, Long accountId) {
        validateModifyInfoDataFormat(request);


        Vet vet = vetPersist.findByAccountId(accountId)
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        List<String> imageUrls = request.getImageUrls();

        String updateImageUrl = "";
        if (imageUrls != null && !imageUrls.isEmpty()) {
            updateImageUrl = imageUrls.get(0);
        }
        Vet updatedVet = VetMapper.updateWithUpdateInfo(vet, request, updateImageUrl);
        vetPersist.save(updatedVet);

        return AccountResp.builder()
                .requestResult("병원 프로필 수정 완료.")
                .build();
    }

    @Transactional(readOnly = true)
    public WithdrawInfoResp getWithdrawInfo(Long accountId) {   //TODO 탈퇴 후 추가로 처리해야할 작업들 고려하기 (ex. 예약취소)
        Vet savedVet = vetPersist.findByAccountId(accountId)
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        Optional<List<Reservation>> reservations = reservationPersist.findByRecipientIdAndReservationStatus(savedVet.getVetId(), ReservationStatus.DEPOSIT_PAID);
        Integer count = reservations.map(List::size).orElse(0);

        return WithdrawInfoResp.builder()
                .waitingForServiceCount(count)
                .build();
    }

    @Transactional
    public WithdrawResp withdraw(Long accountId) {  //TODO 탈퇴 후 추가로 처리해야할 작업들 고려하기 (ex. 예약취소)
        Vet savedVet = vetPersist.findByAccountId(accountId)
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        vetPersist.deleteByAccountId(savedVet.getAccountId());

        return WithdrawResp.builder()
                .accountId(savedVet.getAccountId())
                .withdrawDate(LocalDateTime.now())
                .build();
    }

    private void validateModifyInfoDataFormat(UpdateInfo request) {
        Vet.validateTimeRange(request.getStartTime(), request.getEndTime());
        Vet.validateClosedDays(request.getClosedDays());
        Vet.validatePhoneNumber(request.getPhoneNumber());
        Vet.validateIntroduction(request.getIntroduction());
        Vet.validateImageUrlList(request.getImageUrls());
    }
}
