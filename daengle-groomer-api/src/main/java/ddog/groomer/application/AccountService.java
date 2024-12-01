package ddog.groomer.application;

import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.domain.groomer.Groomer;
import ddog.groomer.application.mapper.GroomerMapping;
import ddog.groomer.presentation.account.dto.SignUpReq;
import ddog.groomer.presentation.account.dto.SignUpResp;
import ddog.persistence.port.AccountPersist;
import ddog.persistence.port.GroomerPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountPersist accountPersist;
    private final GroomerPersist groomerPersist;

    public SignUpResp signUp(SignUpReq request) {
        Account newAccount = Account.create(request.getEmail(), Role.GROOMER);
        Account savedAccount = accountPersist.save(newAccount);

        Groomer newGroomer = GroomerMapping.create(savedAccount.getAccountId(), request);
        groomerPersist.save(newGroomer);

        return SignUpResp.builder()
                .isRegistered(true)
                .build();
    }
}
