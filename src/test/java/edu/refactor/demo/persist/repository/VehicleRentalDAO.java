package edu.refactor.demo.persist.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRentalDAO extends CrudRepository<VehicleRental, Long> {
}
