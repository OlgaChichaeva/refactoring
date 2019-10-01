package edu.refactor.demo.services.impl;

import edu.refactor.demo.entities.Vehicle;
import edu.refactor.demo.exceptions.DuplicateObjectException;
import edu.refactor.demo.exceptions.NotFoundException;
import edu.refactor.demo.projecttypes.VehicleStatus;
import edu.refactor.demo.repository.VehicleRepo;
import edu.refactor.demo.services.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static edu.refactor.demo.projecttypes.VehicleStatus.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service(VehicleService.NAME)
public class VehicleServiceBean implements VehicleService {

    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
    public VehicleServiceBean(VehicleRepo vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    @Override
    public List<Vehicle> getAllVehicle() {
        return (List<Vehicle>) vehicleRepo.findAll();
    }

    @Override
    public void createVehicle(Vehicle vehicleModel) {
        if (isNull(vehicleRepo.findById(vehicleModel.getId()))) {
            vehicleRepo.save(vehicleModel);
        } else {
            String errorMessage = String.format("Vehicle: id=%s already exists", vehicleModel.getId());
            log.error(errorMessage);
            throw new DuplicateObjectException(errorMessage);
        }
    }

    @Override
    public void updateStatusByNumber(String serialNumber, String status) {
        VehicleStatus nextStatus = valueOf(status);

        Vehicle vehicle = vehicleRepo.findBySerialNumber(serialNumber);
        if (nonNull(vehicle)) {

            if (vehicle.getStatus().equals(OPEN) && (nextStatus.equals(DELETE) || nextStatus.equals(RESERVED))) {
                updateVehicleStatus(nextStatus, vehicle);
            }

            if (vehicle.getStatus().equals(LEASED) && (nextStatus.equals(RETURNED) || nextStatus.equals(LOST))) {
                updateVehicleStatus(nextStatus, vehicle);
            }

            if (vehicle.getStatus().equals(RETURNED) && (nextStatus.equals(SERVICE) || nextStatus.equals(OPEN))) {
                updateVehicleStatus(nextStatus, vehicle);
            }

            if (vehicle.getStatus().equals(RESERVED) && (nextStatus.equals(LEASED))) {
                updateVehicleStatus(nextStatus, vehicle);
            }

            if (vehicle.getStatus().equals(SERVICE) && (nextStatus.equals(OPEN))) {
                updateVehicleStatus(nextStatus, vehicle);
            }
        } else {
            String errorMessage = String.format("Vehicle: serialNumber=%s not Found", serialNumber);
            log.error(errorMessage);
            throw new NotFoundException(errorMessage);
        }
    }

    private void updateVehicleStatus(VehicleStatus nextStatus, Vehicle vehicle) {
        vehicle.setStatus(nextStatus);
        vehicleRepo.save(vehicle);
    }
}