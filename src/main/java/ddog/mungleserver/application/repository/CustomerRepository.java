package ddog.mungleserver.application.repository;

import ddog.mungleserver.domain.Customer;
import ddog.mungleserver.global.auth.config.enums.Provider;

public interface CustomerRepository {

    boolean notExistsAccountByEmailAndProvider(String email, Provider provider);

    boolean existsByEmailAndProvider(String email, Provider provider);

    Customer findByEmailAndProvider(String email, Provider provider);

    void save(Customer customer);
}
