package edu.refactor.demo.repository;

import edu.refactor.demo.entities.VehicleRental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRentalRepo extends CrudRepository<VehicleRental, Long> {
}
