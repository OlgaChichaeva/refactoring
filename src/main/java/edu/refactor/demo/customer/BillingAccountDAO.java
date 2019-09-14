package edu.refactor.demo.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingAccountDAO extends CrudRepository<BillingAccount, Long> {
}