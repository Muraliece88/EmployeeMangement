package com.abnamro.nl.constants;

import com.abnamro.nl.exceptions.NoRoleException;

/**
 *
 * Roles enum to allow only valid roles
 */
public enum RolesEnum {
    MANGER(1),
    ADMIN(2),
    USER(3);
    public final long label;

    private RolesEnum(long label) {
        this.label = label;
    }
    public static String getNameByLabel(long label) {
        for (RolesEnum role : RolesEnum.values()) {
            if (role.label == label) {
                return role.name();
            }
        }
        throw new NoRoleException("No role found with label: " + label);
    }

}
