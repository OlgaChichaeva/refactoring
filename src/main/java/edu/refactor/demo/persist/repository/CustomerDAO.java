package edu.refactor.demo.persist.repository;

import edu.refactor.demo.persist.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDAO extends CrudRepository<Customer, Long> {
}