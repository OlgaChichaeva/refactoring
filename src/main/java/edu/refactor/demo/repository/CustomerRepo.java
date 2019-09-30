package edu.refactor.demo.repository;

import edu.refactor.demo.entities.BillingAccount;
import edu.refactor.demo.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends CrudRepository<Customer, Long> {

    Customer findByLoginAndEmail(String login, String email);

    @Query("select e from Customer e where e.status = :status")
    List<Customer> findCustomersByStatus(@Param("status") String status);

    @Query("select b from Customer e join e.billingAccounts b where e.email = :email and e.login = :login")
    List<BillingAccount> getCustomerBillingAccounts(@Param("email") String email, @Param("login") String login);

}