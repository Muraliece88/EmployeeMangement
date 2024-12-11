package com.abnamro.nl.constants;

public enum RolesEnum {
    MANGER(1),
    ADMIN(2),
    USER(3);
    public final long label;

    private RolesEnum(long label) {
        this.label = label;
    }
}
