package ddog.mungleserver.persistence.Customer;

import ddog.mungleserver.infrastructure.auth.config.enums.Provider;
import ddog.mungleserver.jpa.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select case when count(a) = 0 then true else false end from Customer a where a.email = :email and a.provider = :provider")
    boolean notExistsAccountByEmailAndProvider(String email, Provider provider);

    Optional<Customer> findByEmailAndProvider(String email, Provider provider);
}
