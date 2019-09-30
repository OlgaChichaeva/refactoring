package edu.refactor.demo.services;

import edu.refactor.demo.entities.Vehicle;

import java.util.List;

public interface VehicleService {
    String NAME = "demo_VehicleService";

    List<Vehicle> getAllVehicle();

    void createVehicle(Vehicle vehicleModel);

    void updateStatusByNumber(String serialNumber, String nextStatus);
}
