package edu.refactor.demo.exceptions;

public class VehicleRentalException extends  IllegalStateException {

    public VehicleRentalException(String message) {
        super(message);
    }
}
