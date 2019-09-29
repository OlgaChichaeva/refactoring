package edu.refactor.demo.projecttypes;

import org.springframework.lang.Nullable;

public enum VehicleRentalStatus {
    ACTIVE("ACTIVE"),
    COMPLETED("COMPLETED"),
    CREATED("CREATED"),
    EXPIRED("EXPIRED");


    private String id;

    VehicleRentalStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Currency fromId(String id) {
        for (Currency at : Currency.values()) {
            if (at.getId().equalsIgnoreCase(id)) {
                return at;
            }
        }
        return null;
    }
}
