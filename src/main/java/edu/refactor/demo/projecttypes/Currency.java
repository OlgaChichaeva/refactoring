package edu.refactor.demo.projecttypes;

import org.springframework.lang.Nullable;

public enum Currency {

    DOLLAR("DOLLAR"),
    EURO("EURO"),
    RUBLE("RUBLE");

    private String id;

    Currency(String value) {
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
