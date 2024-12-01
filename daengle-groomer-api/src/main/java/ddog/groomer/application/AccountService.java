package ddog.groomer.application;

import ddog.auth.config.jwt.JwtTokenProvider;
import ddog.auth.dto.LoginResult;
import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.domain.groomer.Groomer;
import ddog.groomer.application.mapper.GroomerMapping;
import ddog.groomer.presentation.account.dto.ModifyInfoReq;
import ddog.groomer.presentation.account.dto.ProfileInfo;
import ddog.groomer.presentation.account.dto.SignUpReq;
import ddog.groomer.presentation.account.dto.SignUpResp;
import ddog.persistence.port.AccountPersist;
import ddog.persistence.port.GroomerPersist;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountPersist accountPersist;
    private final GroomerPersist groomerPersist;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResp signUp(SignUpReq request, HttpServletResponse response) {
        Account newAccount = Account.create(request.getEmail(), Role.GROOMER);
        Account savedAccount = accountPersist.save(newAccount);

        Groomer newGroomer = GroomerMapping.create(savedAccount.getAccountId(), request);
        groomerPersist.save(newGroomer);

        LoginResult loginResult = jwtTokenProvider
                .generateToken(getAuthentication(savedAccount.getAccountId(), request.getEmail()), Role.GROOMER, response);

        return SignUpResp.builder()
                .accessToken(loginResult.getAccessToken())
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
        return GroomerMapping.toModifyPage(groomer);
    }

    @Transactional
    public void modifyInfo(ModifyInfoReq request, Long accountId) {
        Groomer groomer = groomerPersist.getGroomerByAccountId(accountId);
        Groomer updatedGroomer = GroomerMapping.withUpdate(groomer, request);
        groomerPersist.save(updatedGroomer);
    }


}
