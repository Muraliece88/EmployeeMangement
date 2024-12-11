package com.abnamro.nl.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RolesEnumTest {

    @Test
    void values() {
        assertEquals(1, RolesEnum.MANGER.label);
        assertEquals(2, RolesEnum.ADMIN.label);
        assertEquals(3, RolesEnum.USER.label);
    }

    @Test
    void valueOf() {
        assertSame(RolesEnum.MANGER, RolesEnum.valueOf("MANGER"));
        assertSame(RolesEnum.ADMIN, RolesEnum.valueOf("ADMIN"));
        assertSame(RolesEnum.USER, RolesEnum.valueOf("USER"));
    }
}