package edu.refactor.demo.repository;

import edu.refactor.demo.entities.Money;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyRepo extends CrudRepository<Money, Long> {
}
