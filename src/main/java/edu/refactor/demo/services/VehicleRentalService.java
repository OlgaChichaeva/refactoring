package edu.refactor.demo.services;

import edu.refactor.demo.entities.VehicleRental;

import java.util.List;

public interface VehicleRentalService {
    String NAME = "demo_VerhicleRentailService";


    List<VehicleRental> getAllVehicleRental();
    void createRental(VehicleRental rental);

    void activateRentalStatus(Long rentalId);

    void expairyRentalStatus(Long rentalId);

    void completeVehicle(Long rentalId);
}
