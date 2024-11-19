package ddog.mungleserver.infrastructure;

import ddog.mungleserver.application.repository.CustomerRepository;
import ddog.mungleserver.domain.Customer;
import ddog.mungleserver.global.auth.config.enums.Provider;
import ddog.mungleserver.implementation.CustomerException;
import ddog.mungleserver.implementation.enums.CustomerExceptionType;
import ddog.mungleserver.infrastructure.jpa.CustomerJpaEntity;
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
