package ddog.vet.application;

import ddog.auth.config.jwt.JwtTokenProvider;
import ddog.auth.dto.LoginResult;
import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.domain.groomer.Groomer;
import ddog.domain.vet.Vet;
import ddog.persistence.port.AccountPersist;
import ddog.persistence.port.VetPersist;
import ddog.vet.application.mapper.VetMapper;
import ddog.vet.presentation.account.dto.ModifyInfoReq;
import ddog.vet.presentation.account.dto.ProfileInfo;
import ddog.vet.presentation.account.dto.SignUpReq;
import ddog.vet.presentation.account.dto.SignUpResp;
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
    private final VetPersist vetPersist;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResp signUp(SignUpReq request, HttpServletResponse response) {
        Account newAccount = Account.create(request.getEmail(), Role.VET);
        Account savedAccount = accountPersist.save(newAccount);

        Vet newVet = VetMapper.create(savedAccount.getAccountId(), request);
        vetPersist.save(newVet);

        LoginResult loginResult = jwtTokenProvider
                .generateToken(getAuthentication(savedAccount.getAccountId(), request.getEmail()), Role.VET, response);

        return SignUpResp.builder()
                .accessToken(loginResult.getAccessToken())
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
        Vet vet = vetPersist.getVetByAccountId(accountId);
        Vet updatedVet = VetMapper.withUpdate(vet, request);
        vetPersist.save(updatedVet);
    }


}
