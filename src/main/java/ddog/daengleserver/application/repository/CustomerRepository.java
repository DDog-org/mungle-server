package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.Customer;
import ddog.daengleserver.global.auth.config.enums.Provider;

public interface CustomerRepository {

    boolean checkExistsAccountBy(String email, Provider provider);

    Customer findCustomerBy(String email, Provider provider);

    void save(Customer customer);
}
