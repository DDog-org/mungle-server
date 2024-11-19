package ddog.mungleserver.application.repository;

import ddog.mungleserver.domain.Customer;
import ddog.mungleserver.global.auth.config.enums.Provider;

public interface CustomerRepository {

    boolean checkExistsAccountBy(String email, Provider provider);

    Customer findCustomerBy(String email, Provider provider);

    void save(Customer customer);
}
