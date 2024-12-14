package ddog.groomer.application;

import ddog.auth.config.jwt.JwtTokenProvider;
import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.domain.groomer.Groomer;
import ddog.domain.groomer.License;
import ddog.domain.groomer.port.LicensePersist;
import ddog.domain.shop.BeautyShop;
import ddog.domain.shop.port.BeautyShopPersist;
import ddog.groomer.application.exception.account.GroomerException;
import ddog.groomer.application.exception.account.GroomerExceptionType;
import ddog.groomer.application.mapper.GroomerMapper;
import ddog.groomer.presentation.account.dto.*;
import ddog.domain.account.port.AccountPersist;
import ddog.domain.groomer.port.GroomerPersist;
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
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountPersist accountPersist;
    private final GroomerPersist groomerPersist;
    private final LicensePersist licensePersist;
    private final BeautyShopPersist beautyShopPersist;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResp signUp(SignUpReq request, HttpServletResponse response) {

        validateSignUpReqDataFormat(request);

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
        Long shopId = beautyShopPersist.findBeautyShopByNameAndAddress(savedBeautyShop.getShopName(), savedBeautyShop.getShopAddress()).get().getShopId();;

        Groomer newGroomer = GroomerMapper.create(savedAccount.getAccountId(), request, licenses, shopId);
        groomerPersist.save(newGroomer);

        return SignUpResp.builder()
                .accessToken(accessToken)
                .build();
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
}
