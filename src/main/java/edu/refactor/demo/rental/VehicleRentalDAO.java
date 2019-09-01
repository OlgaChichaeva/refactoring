package edu.refactor.demo.rental;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRentalDAO extends CrudRepository<VehicleRental, Long> {
}
