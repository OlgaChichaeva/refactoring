package edu.refactor.demo.services.impl;

import edu.refactor.demo.entities.Vehicle;
import edu.refactor.demo.repository.VehicleRepo;
import edu.refactor.demo.services.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

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
            log.error(String.format("Vehicle: id=%s already exists", vehicleModel.getId()));
        }
    }

    @Override
    public void updateStatusByNumber(String serialNumber, String nextStatus) {
        Vehicle vehicle = vehicleRepo.findBySerialNumber(serialNumber);

        if (vehicle.getStatus().equals("open") && (nextStatus.equals("delete") || nextStatus.equals("reserved"))) {
            updateVehicleStatus(nextStatus, vehicle);
        }

        if (vehicle.getStatus().equals("leased") && (nextStatus.equals("returned") || nextStatus.equals("lost"))) {
            updateVehicleStatus(nextStatus, vehicle);
        }

        if (vehicle.getStatus().equals("returned") && (nextStatus.equals("service") || nextStatus.equals("open"))) {
            updateVehicleStatus(nextStatus, vehicle);
        }

        if (vehicle.getStatus().equals("reserved") && (nextStatus.equals("leased"))) {
            updateVehicleStatus(nextStatus, vehicle);
        }

        if (vehicle.getStatus().equals("service") && (nextStatus.equals("open"))) {
            updateVehicleStatus(nextStatus, vehicle);
        }
    }

    private void updateVehicleStatus(String nextStatus, Vehicle vehicle) {
        try {
            vehicle.setStatus(nextStatus);
            vehicleRepo.save(vehicle);
        } catch (VehicleException e) {
            log.error(String.format("Vehicle status did't modify! %s", e.getMessage()));
        }
    }
}