package edu.refactor.demo.services.impl;

import edu.refactor.demo.entities.*;
import edu.refactor.demo.exceptions.NotFoundException;
import edu.refactor.demo.exceptions.NotModifiedException;
import edu.refactor.demo.exceptions.VehicleRentalException;
import edu.refactor.demo.projecttypes.VehicleRentalStatus;
import edu.refactor.demo.repository.BillingAccountRepo;
import edu.refactor.demo.repository.CustomerRepo;
import edu.refactor.demo.repository.VehicleRentalRepo;
import edu.refactor.demo.repository.VehicleRepo;
import edu.refactor.demo.services.CashService;
import edu.refactor.demo.services.VehicleRentalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static edu.refactor.demo.projecttypes.VehicleRentalStatus.*;

@Slf4j
@Service(VehicleRentalService.NAME)
public class VehicleRentalServiceBean implements VehicleRentalService {

    @Autowired
    private final VehicleRentalRepo vehicleRentalRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
    private CashService cashService;

    @Autowired
    private BillingAccountRepo billingAccountRepo;

    public VehicleRentalServiceBean(VehicleRentalRepo vehicleRentalRepo, CustomerRepo customerRepo,
                                    VehicleRepo vehicleRepo, CashService cashService, BillingAccountRepo billingAccountRepo) {
        this.vehicleRentalRepo = vehicleRentalRepo;
        this.customerRepo = customerRepo;
        this.billingAccountRepo = billingAccountRepo;
        this.vehicleRepo = vehicleRepo;
        this.cashService = cashService;
    }

    @Override
    public List<VehicleRental> getAllVehicleRental() {
        return (List<VehicleRental>) vehicleRentalRepo.findAll();
    }

    @Override
    public void completeVehicle(Long rentalId) {
        Optional<VehicleRental> rental = vehicleRentalRepo.findById(rentalId);
        if (rental.isPresent()) {
            VehicleRental vehicleRental = rental.get();
            if (VehicleRentalStatus.ACTIVE.equals(vehicleRental.getStatus())) {
                List<BillingAccount> billingAccounts = vehicleRental.getCustomer().getBillingAccounts();
                Money value = vehicleRental.getVehicle().getPrice();
                for (BillingAccount ba : billingAccounts) {
                    Money v = cashService.subtract(ba.getMoney(), value);

                    if (v.getCount().compareTo(BigDecimal.ZERO) >= 0) {
                        value = cashService.subtract(value, v);
                        ba.setMoney(v);
                    } else {
                        value = cashService.subtract(value, ba.getMoney());
                        Money zero = new Money();
                        zero.setCount(BigDecimal.ZERO);
                        ba.setMoney(zero);
                    }
                }
                if (value.getCount().compareTo(BigDecimal.ZERO) < 0) {
                    String errorMessage = "Vehicle rental price < 0";
                    log.error(errorMessage);
                    throw new VehicleRentalException(errorMessage);
                }
                vehicleRental.setEndRent(Instant.now());
                vehicleRental.setStatus(VehicleRentalStatus.COMPLETED);
                billingAccountRepo.saveAll(billingAccounts);
                vehicleRentalRepo.save(vehicleRental);
            } else {
                String errorMessage = String.format("vehicle is status = %s can,t be complete !", vehicleRental.getStatus());
                log.error(errorMessage);
                throw new VehicleRentalException(errorMessage);

            }
        } else {
            String errorMessage = String.format("VehicleRental: id=%s not Found", rentalId);
            log.error(errorMessage);
            throw new NotFoundException(errorMessage);
        }
    }


    @Override
    public void activateRentalStatus(Long rentalId) {
        Optional<VehicleRental> vehicleRental = vehicleRentalRepo.findById(rentalId);
        if (vehicleRental.isPresent()) {
            VehicleRentalStatus currentStatus = vehicleRental.get().getStatus();
            if (currentStatus.equals(CREATED) || currentStatus.equals(EXPIRED)) {
                updateRentalStatus(vehicleRental.get(), ACTIVE);
            } else {
                String errorMessage = String.format("Current status = %s does't allow activation rental!", currentStatus.getId());
                log.error(errorMessage);
                throw new NotModifiedException(errorMessage);
            }
        } else {
            String errorMessage = String.format("Vehicle rental: id=%s not found", rentalId);
            log.error(errorMessage);
            throw new NotFoundException(errorMessage);
        }
    }

    @Override
    public void expairyRentalStatus(Long rentalId) {
        Optional<VehicleRental> vehicleRental = vehicleRentalRepo.findById(rentalId);
        if (vehicleRental.isPresent()) {
            VehicleRentalStatus currentStatus = vehicleRental.get().getStatus();
            if (currentStatus.equals(ACTIVE)) {
                updateRentalStatus(vehicleRental.get(), EXPIRED);
            } else {
                String errorMessage = String.format("Current status = %s does't allow expaired rental!", currentStatus.getId());
                log.error(errorMessage);
                throw new NotModifiedException(errorMessage);
            }
        } else {
            String errorMessage = String.format("Vehicle rental: id=%s not found", rentalId);
            log.error(errorMessage);
            throw new NotFoundException(errorMessage);
        }
    }

    private void updateRentalStatus(VehicleRental rental, VehicleRentalStatus status) {
        rental.setEndRent(Instant.now());
        rental.setStatus(status);
        vehicleRentalRepo.save(rental);
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
        String errorMessage = String.format("Vehicle rental: rentalId = %s or customerId= %s not found", rentalId, customerId);
        log.error(errorMessage);
        throw new NotFoundException(errorMessage);
    }
}
