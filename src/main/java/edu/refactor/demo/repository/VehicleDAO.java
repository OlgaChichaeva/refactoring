package edu.refactor.demo.repository;

import edu.refactor.demo.entities.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleDAO extends CrudRepository<Vehicle, Long> {
}