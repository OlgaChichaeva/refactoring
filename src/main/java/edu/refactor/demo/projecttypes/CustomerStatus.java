package edu.refactor.demo.projecttypes;

import org.springframework.lang.Nullable;

public enum CustomerStatus {
    ACTIVE("ACTIVE"),
    FREEZE("FREEZE"),
    VIP("VIP");

    private String id;

    CustomerStatus(String value) {
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
