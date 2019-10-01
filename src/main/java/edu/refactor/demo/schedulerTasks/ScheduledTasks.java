package edu.refactor.demo.schedulerTasks;

import edu.refactor.demo.entities.VehicleRental;
import edu.refactor.demo.projecttypes.VehicleRentalStatus;
import edu.refactor.demo.repository.VehicleRentalRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

import static edu.refactor.demo.projecttypes.CustomerStatus.*;

@Component
public class ScheduledTasks {
    private final VehicleRentalRepo vehicleRentalRepo;

    @Autowired
    public ScheduledTasks(VehicleRentalRepo vehicleRentalRepo) {
        this.vehicleRentalRepo = vehicleRentalRepo;
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        Iterable<VehicleRental> vehicleRentals = vehicleRentalRepo.findAll();

        for (VehicleRental vr : vehicleRentals) {
            if (ACTIVE.equals(vr.getStatus())) {
                Duration interval = Duration.between(vr.getStartRent(), Instant.now());
                if (DEFAULT.equals(vr.getCustomer().getStatus())) {
                    if (!interval.minusHours(24).isNegative()) {
                        vr.setStatus(VehicleRentalStatus.EXPIRED);
                    }
                } else if (VIP.equals(vr.getCustomer().getStatus())) {
                    if (!interval.minusHours(72).isNegative()) {
                        vr.setStatus(VehicleRentalStatus.EXPIRED);
                    }
                }
            }
            vehicleRentalRepo.saveAll(vehicleRentals);
        }
    }

}