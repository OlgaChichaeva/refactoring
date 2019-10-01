package edu.refactor.demo.controllers;

import edu.refactor.demo.entities.Vehicle;
import edu.refactor.demo.exceptions.DuplicateObjectException;
import edu.refactor.demo.exceptions.NotFoundException;
import edu.refactor.demo.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/vehicle")
@RestController
public class VehicleController {

    private VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/create")
    public ResponseEntity createVehicle(@RequestBody Vehicle vehicleModel) {
        try {
            vehicleService.createVehicle(vehicleModel);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DuplicateObjectException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllVehicle() {
        try {
            List<Vehicle> vehicles = vehicleService.getAllVehicle();
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/status/update/serialNumber/{serialNumber}/status/{status}")
    public ResponseEntity updateStatus(@RequestParam(name = "serialNumber") String serialNumber,
                                       @RequestParam(name = "status") String nextStatus) {
        try {
            vehicleService.updateStatusByNumber(serialNumber, nextStatus);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }
}