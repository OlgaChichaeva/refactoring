package edu.refactor.demo.controllers;

import edu.refactor.demo.services.VehicleRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.refactor.demo.entities.Vehicle;


@RequestMapping("/rental")
@RestController
public class VehicleRentalController {

    private VehicleRentalService rentalService;

    @Autowired
    public VehicleRentalController(VehicleRentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<VehicleRental>> getAllVehicleRental() {
        try {
            List<VehicleRental> vehicles = rentalService.getAllVehicleRental();
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/{rental}/active")
    public ResponseEntity<Void> activeVehicle(@RequestParam(name = "rental") Long rentalId) {
        try {
            rentalService.activateRentalStatus(rentalId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/{rental}/expired")
    public ResponseEntity<Void> expiredVehicle(@RequestParam(name = "rental") Long rentalId) {
        try {
            rentalService.expairyRentalStatus(rentalId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/create/rental")
    public ResponseEntity<Void> createVehicleRental(@RequestBody VehicleRental rental) {
        try {
            rentalService.createRental(rental);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{rentalId}/complete/")
    public ResponseEntity<Void> completeVehicle(@RequestParam(name = "rentalId") Long rentalId) {
        try {
            rentalService.completeVehicle(rentalId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}
