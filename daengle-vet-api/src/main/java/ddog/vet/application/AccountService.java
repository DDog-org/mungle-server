package ddog.vet.application;

import ddog.auth.config.jwt.JwtTokenProvider;
import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.domain.vet.Vet;
import ddog.persistence.port.AccountPersist;
import ddog.persistence.port.VetPersist;
import ddog.vet.application.exception.VetException;
import ddog.vet.application.exception.VetExceptionType;
import ddog.vet.application.mapper.VetMapper;
import ddog.vet.presentation.account.dto.ModifyInfoReq;
import ddog.vet.presentation.account.dto.ProfileInfo;
import ddog.vet.presentation.account.dto.SignUpReq;
import ddog.vet.presentation.account.dto.SignUpResp;
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

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountPersist accountPersist;
    private final VetPersist vetPersist;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResp signUp(SignUpReq request, HttpServletResponse response) {
        if(this.hasInValidSignUpRequestDataFormat(request))
            throw new VetException(VetExceptionType.INVALID_REQUEST_DATA_FORMAT);

        Account newAccount = Account.create(request.getEmail(), Role.VET);
        Account savedAccount = accountPersist.save(newAccount);

        Vet newVet = VetMapper.create(savedAccount.getAccountId(), request);
        vetPersist.save(newVet);

        Authentication authentication = getAuthentication(savedAccount.getAccountId(), request.getEmail());
        String accessToken = jwtTokenProvider.generateToken(authentication, response);

        return SignUpResp.builder()
                .accessToken(accessToken)
                .build();
    }

    private Authentication getAuthentication(Long accountId, String email) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_VET"));

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(email + "," + accountId, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @Transactional(readOnly = true)
    public ProfileInfo.ModifyPage getModifyPage(Long accountId) {
        Vet vet = vetPersist.getVetByAccountId(accountId);
        return VetMapper.toModifyPage(vet);
    }

    @Transactional
    public void modifyInfo(ModifyInfoReq request, Long accountId) {
        this.hasInValidModifyInfoDataFormat(request);

        Vet vet = vetPersist.getVetByAccountId(accountId);
        Vet updatedVet = VetMapper.withUpdate(vet, request);
        vetPersist.save(updatedVet);
    }

    private boolean hasInValidSignUpRequestDataFormat(SignUpReq request) {
        try {
            Vet.validateName(request.getName());
            Vet.validateAddress(request.getAddress());
            Vet.validateDetailAddress(request.getDetailAddress());
            Vet.validatePhoneNumber(request.getPhoneNumber());
            Vet.validateLicenses(request.getLicenses());

            return false; // 모든 유효성 검사를 통과
        } catch (IllegalArgumentException e) {
            return true; // 유효성 검사 실패
        }
    }

    private void hasInValidModifyInfoDataFormat(ModifyInfoReq request) {
        Vet.validateTimeRange(request.getStartTime(), request.getEndTime());
        Vet.validateClosedDays(request.getClosedDays());
        Vet.validatePhoneNumber(request.getPhoneNumber());
        Vet.validateIntroduction(request.getIntroduction());
    }
}
