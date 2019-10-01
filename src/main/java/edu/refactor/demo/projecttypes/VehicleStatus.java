package edu.refactor.demo.projecttypes;

import org.springframework.lang.Nullable;

public enum VehicleStatus {
    OPEN("OPEN"),
    DELETE("DELETE"),
    RESERVED("RESERVED"),
    LEASED("LEASED"),
    LOST("LOST"),
    RETURNED("RETURNED"),
    SERVICE("SERVICE");

    private String id;

    VehicleStatus(String value) {
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
