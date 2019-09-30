package edu.refactor.demo.services;

import edu.refactor.demo.entities.Money;
import edu.refactor.demo.entities.Vehicle;
import edu.refactor.demo.repository.VehicleDAO;
import edu.refactor.demo.projecttypes.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class VehicleService {
    private final VehicleDAO vehicleDAO;

    @Autowired
    public VehicleService(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @RequestMapping(value = "/vehicle", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Vehicle> all() {
        return vehicleDAO.findAll();
    }

    @RequestMapping(value = "/vehicle/status/update", method = RequestMethod.POST)
    public @ResponseBody
    boolean statusUpdate(@RequestParam(name = "serialNumber") String serialNumber, @RequestParam(name = "status") String nextStatus) {
        if (nextStatus.equals("delete")) {
            for (Vehicle vehicle : vehicleDAO.findAll()) {
                if (vehicle.serialNumber.equals(serialNumber)) {
                    if (vehicle.status.equals("open")) {
                        vehicle.status = (nextStatus);
                        vehicleDAO.save(vehicle);
                        return true;
                    }
                    return false;
                }
            }
        }
        if (nextStatus.equals("reserved")) {
            for (Vehicle vehicle : vehicleDAO.findAll()) {
                if (vehicle.serialNumber.equals(serialNumber)) {
                    if (vehicle.status.equals("open")) {
                        vehicle.status = (nextStatus);
                        vehicleDAO.save(vehicle);
                        return true;
                    }
                    return false;
                }
            }
        }
        if (nextStatus.equals("leased")) {
            for (Vehicle vehicle : vehicleDAO.findAll()) {
                if (vehicle.serialNumber.equals(serialNumber)) {
                    if (vehicle.status.equals("reserved")) {
                        vehicle.status = (nextStatus);
                        vehicleDAO.save(vehicle);
                        return true;
                    }
                    return false;
                }
            }
        }

        if (nextStatus.equals("lost")) {
            for (Vehicle vehicle : vehicleDAO.findAll()) {
                if (vehicle.serialNumber.equals(serialNumber)) {
                    if (vehicle.status.equals("leased")) {
                        vehicle.status = (nextStatus);
                        vehicleDAO.save(vehicle);
                        return true;
                    }
                    return false;
                }
            }
        }

        if (nextStatus.equals("returned")) {
            for (Vehicle vehicle : vehicleDAO.findAll()) {
                if (vehicle.serialNumber.equals(serialNumber)) {
                    if (vehicle.status.equals("leased")) {
                        vehicle.status = (nextStatus);
                        vehicleDAO.save(vehicle);
                        return true;
                    }
                    return false;
                }
            }
        }

        if (nextStatus.equals("service")) {
            for (Vehicle vehicle : vehicleDAO.findAll()) {
                if (vehicle.serialNumber.equals(serialNumber)) {
                    if (vehicle.status.equals("returned")) {
                        vehicle.status = (nextStatus);
                        vehicleDAO.save(vehicle);
                        return true;
                    }
                    return false;
                }
            }
        }


        if (nextStatus.equals("open")) {
            for (Vehicle vehicle : vehicleDAO.findAll()) {
                if (vehicle.serialNumber.equals(serialNumber)) {
                    if (vehicle.status.equals("service")) {
                        vehicle.status = (nextStatus);
                        vehicleDAO.save(vehicle);
                        return true;
                    }
                    return false;
                }
            }
        }
        if (nextStatus.equals("open")) {
            for (Vehicle vehicle : vehicleDAO.findAll()) {
                if (vehicle.serialNumber.equals(serialNumber)) {
                    if (vehicle.status.equals("returned")) {
                        vehicle.status = (nextStatus);
                        vehicleDAO.save(vehicle);
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    @RequestMapping(value = "/vehicle/create", method = RequestMethod.POST)
    public @ResponseBody
    Vehicle createVehicle(@RequestParam(name = "title") String title,
                          @RequestParam(name = "price") double price,
                          @RequestParam(name = "price") String currency,
                          @RequestParam(name = "type") String type,
                          @RequestParam(name = "serialNumber") String serialNumber) {
        Vehicle v = new Vehicle();
        v.status = "open";

        Money vehiclePrice = new Money();
        vehiclePrice.setCount(BigDecimal.valueOf(price));
        vehiclePrice.setCurrency(Currency.fromId(currency));

        v.price = vehiclePrice;
        v.title = title;
        v.type = type;
        v.serialNumber = (serialNumber);
        return vehicleDAO.save(v);
    }
}
