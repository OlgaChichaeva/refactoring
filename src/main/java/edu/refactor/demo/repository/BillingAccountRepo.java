package edu.refactor.demo.repository;

import edu.refactor.demo.entities.BillingAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingAccountRepo extends CrudRepository<BillingAccount, Long> {
}