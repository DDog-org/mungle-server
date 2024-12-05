package ddog.vet.application;

import ddog.auth.config.jwt.JwtTokenProvider;
import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.domain.vet.Vet;
import ddog.persistence.mysql.port.AccountPersist;
import ddog.persistence.mysql.port.VetPersist;
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
        validateSignUpRequestDataFormat(request);

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

    @Transactional(readOnly = true)
    public ProfileInfo.ModifyPage getModifyPage(Long accountId) {
        Vet vet = vetPersist.getVetByAccountId(accountId);
        return VetMapper.toModifyPage(vet);
    }

    @Transactional
    public AccountResp modifyInfo(ModifyInfoReq request, Long accountId) {
        validateModifyInfoDataFormat(request);

        Vet vet = vetPersist.getVetByAccountId(accountId);
        Vet updatedVet = VetMapper.withUpdate(vet, request);
        vetPersist.save(updatedVet);

        return AccountResp.builder()
                .requestResult("병원 프로필 수정 완료.")
                .build();
    }

    private void validateModifyInfoDataFormat(ModifyInfoReq request) {
        Vet.validateTimeRange(request.getStartTime(), request.getEndTime());
        Vet.validateClosedDays(request.getClosedDays());
        Vet.validatePhoneNumber(request.getPhoneNumber());
        Vet.validateIntroduction(request.getIntroduction());
    }
}
