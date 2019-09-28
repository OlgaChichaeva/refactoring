package edu.refactor.demo.persist.repository;

import edu.refactor.demo.persist.entities.BillingAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingAccountDAO extends CrudRepository<BillingAccount, Long> {
}