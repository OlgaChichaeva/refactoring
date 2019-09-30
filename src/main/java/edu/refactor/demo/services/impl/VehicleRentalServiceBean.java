package edu.refactor.demo.services.impl;

import edu.refactor.demo.entities.Customer;
import edu.refactor.demo.entities.Vehicle;
import edu.refactor.demo.entities.VehicleRental;
import edu.refactor.demo.repository.CustomerRepo;
import edu.refactor.demo.repository.VehicleRentalRepo;
import edu.refactor.demo.repository.VehicleRepo;
import edu.refactor.demo.services.VehicleRentalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service(VehicleRentalService.NAME)
public class VehicleRentalServiceBean implements VehicleRentalService {

    @Autowired
    private final VehicleRentalRepo vehicleRentalRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private VehicleRepo vehicleRepo;

    public VehicleRentalServiceBean(VehicleRentalRepo vehicleRentalRepo, CustomerRepo customerRepo, VehicleRepo vehicleRepo) {
        this.vehicleRentalRepo = vehicleRentalRepo;
        this.customerRepo = customerRepo;
        this.vehicleRepo = vehicleRepo;
    }

    @Override
    public List<VehicleRental> getAllVehicleRental() {
        return (List<VehicleRental>) vehicleRentalRepo.findAll();
    }

    @Override
    public void completeVehicle(Long rentalId) {

    }

    @Override
    public void activateRentalStatus(Long rentalId) {
        Optional<VehicleRental> vehicleRental = vehicleRentalRepo.findById(rentalId);
        if (vehicleRental.isPresent()) {
            String currentStatus = vehicleRental.get().getStatus();
            if (currentStatus.equals("created") || currentStatus.equals("expired")) {
                updateRentalStatus(vehicleRental.get(), "active");
            }
        } else {
            log.error(String.format("Vehicle rental: id=%s not found", rentalId));
        }
    }

    @Override
    public void expairyRentalStatus(Long rentalId) {
        Optional<VehicleRental> vehicleRental = vehicleRentalRepo.findById(rentalId);
        if (vehicleRental.isPresent()) {
            String currentStatus = vehicleRental.get().getStatus();
            if (currentStatus.equals("active")) {
                updateRentalStatus(vehicleRental.get(), "expired");
            }
        } else {
            log.error(String.format("Vehicle rental: id=%s not found", rentalId));
        }
    }

    private void updateRentalStatus(VehicleRental rental, String status) {
        try {
            rental.setEndRent(Instant.now());
            rental.setStatus(status);
            vehicleRentalRepo.save(rental);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void createRental(VehicleRental rental) {
        Long rentalId = rental.getCustomer().getId();
        Long customerId = rental.getVehicle().getId();
        Optional<Vehicle> vehicle = vehicleRepo.findById(rentalId);
        Optional<Customer> customer = customerRepo.findById(customerId);
        if (customer.isPresent() && vehicle.isPresent()) {
            vehicleRentalRepo.save(rental);
        }
        log.error(String.format("Vehicle rental: rentalId = %s or customerId= %s not found", rentalId, customerId));
    }
}
