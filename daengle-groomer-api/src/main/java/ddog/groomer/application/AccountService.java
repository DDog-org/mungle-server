package ddog.groomer.application;

import ddog.auth.config.jwt.JwtTokenProvider;
import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.domain.groomer.Groomer;
import ddog.groomer.application.exception.GroomerException;
import ddog.groomer.application.exception.GroomerExceptionType;
import ddog.groomer.application.mapper.GroomerMapper;
import ddog.groomer.presentation.account.dto.ModifyInfoReq;
import ddog.groomer.presentation.account.dto.ProfileInfo;
import ddog.groomer.presentation.account.dto.SignUpReq;
import ddog.groomer.presentation.account.dto.SignUpResp;
import ddog.persistence.port.AccountPersist;
import ddog.persistence.port.GroomerPersist;
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
    private final GroomerPersist groomerPersist;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResp signUp(SignUpReq request, HttpServletResponse response) {
        if (this.hasInvalidSignUpReqDataFormat(request))
            throw new GroomerException(GroomerExceptionType.INVALID_REQUEST_DATA_FORMAT);

        Account newAccount = Account.create(request.getEmail(), Role.GROOMER);
        Account savedAccount = accountPersist.save(newAccount);

        Groomer newGroomer = GroomerMapper.create(savedAccount.getAccountId(), request);
        groomerPersist.save(newGroomer);

        Authentication authentication = getAuthentication(savedAccount.getAccountId(), request.getEmail());
        String accessToken = jwtTokenProvider.generateToken(authentication, response);

        return SignUpResp.builder()
                .accessToken(accessToken)
                .build();
    }

    private Authentication getAuthentication(Long accountId, String email) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_GROOMER"));

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(email + "," + accountId, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @Transactional(readOnly = true)
    public ProfileInfo.ModifyPage getModifyPage(Long accountId) {
        Groomer groomer = groomerPersist.getGroomerByAccountId(accountId);
        return GroomerMapper.toModifyPage(groomer);
    }

    @Transactional
    public void modifyInfo(ModifyInfoReq request, Long accountId) {
        if(this.hasInvalidModifyInfoReqDataFormat(request))
            throw new GroomerException(GroomerExceptionType.INVALID_REQUEST_DATA_FORMAT);

        Groomer groomer = groomerPersist.getGroomerByAccountId(accountId);
        Groomer updatedGroomer = GroomerMapper.withUpdate(groomer, request);
        groomerPersist.save(updatedGroomer);
    }

    private boolean hasInvalidSignUpReqDataFormat(SignUpReq request) {
        try {
            Groomer.validateShopName(request.getShopName());
            Groomer.validateName(request.getName());
            Groomer.validatePhoneNumber(request.getPhoneNumber());
            Groomer.validateAddress(request.getAddress());
            Groomer.validateDetailAddress(request.getDetailAddress());
            Groomer.validateBusinessLicenses(request.getBusinessLicenses());
            Groomer.validateLicenses(request.getLicenses());

            return false; // 모든 유효성 검사 통과
        } catch (IllegalArgumentException e) {
            return true; // 유효성 검사 실패
        }
    }

    private boolean hasInvalidModifyInfoReqDataFormat(ModifyInfoReq request) {
        try {
            Groomer.validateIntroduction(request.getIntroduction());

            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }
}
