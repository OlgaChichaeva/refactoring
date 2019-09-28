package edu.refactor.demo.persist.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface BillingAccountDAO extends CrudRepository<BillingAccount, Long> {
}