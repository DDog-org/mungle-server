package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.CustomerRepository;
import ddog.daengleserver.domain.Customer;
import ddog.daengleserver.global.auth.config.enums.Provider;
import ddog.daengleserver.implementation.CustomerException;
import ddog.daengleserver.implementation.enums.CustomerExceptionType;
import ddog.daengleserver.infrastructure.jpa.CustomerJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    @Override
    public boolean checkExistsAccountBy(String email, Provider provider) {
        return customerJpaRepository.existsByEmailAndProvider(email, provider);
    }

    @Override
    public Customer findCustomerBy(String email, Provider provider) {
        return customerJpaRepository.findByEmailAndProvider(email, provider)
                .orElseThrow(() -> new CustomerException(CustomerExceptionType.CUSTOMER_NOT_EXIST))
                .toModel();
    }

    @Override
    public void save(Customer customer) {
        customerJpaRepository.save(CustomerJpaEntity.from(customer));
    }

}
