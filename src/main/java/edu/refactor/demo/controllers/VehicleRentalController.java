package edu.refactor.demo.controllers;

import edu.refactor.demo.entities.VehicleRental;
import edu.refactor.demo.exceptions.DuplicateObjectException;
import edu.refactor.demo.exceptions.NotFoundException;
import edu.refactor.demo.exceptions.NotModifiedException;
import edu.refactor.demo.exceptions.VehicleRentalException;
import edu.refactor.demo.services.VehicleRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/rental")
@RestController
public class VehicleRentalController {

    private VehicleRentalService rentalService;

    @Autowired
    public VehicleRentalController(VehicleRentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/create/rental")
    public ResponseEntity createVehicleRental(@RequestBody VehicleRental rental) {
        try {
            rentalService.createRental(rental);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DuplicateObjectException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllVehicleRental() {
        try {
            List<VehicleRental> vehicles = rentalService.getAllVehicleRental();
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{rentalId}/active")
    public ResponseEntity activeVehicle(@RequestParam(name = "rentalId") Long rentalId) {
        try {
            rentalService.activateRentalStatus(rentalId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotModifiedException | NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/{rentalId}/expired")
    public ResponseEntity expiredVehicle(@RequestParam(name = "rentalId") Long rentalId) {
        try {
            rentalService.expairyRentalStatus(rentalId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotModifiedException | NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("{rentalId}/complete/")
    public ResponseEntity completeVehicle(@RequestParam(name = "rentalId") Long rentalId) {
        try {
            rentalService.completeVehicle(rentalId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (VehicleRentalException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }
}
