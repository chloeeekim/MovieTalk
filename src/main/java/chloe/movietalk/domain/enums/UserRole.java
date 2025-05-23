package chloe.movietalk.domain.enums;

import chloe.movietalk.exception.global.InvalidEnumValueException;

import java.util.Arrays;

public enum UserRole {
    ADMIN,
    USER;

    public static UserRole from(String role) {
        if (role == null)
            return null;
        return Arrays.stream(values())
                .filter(g -> g.name().equals(role))
                .findFirst()
                .orElseThrow(() -> InvalidEnumValueException.EXCEPTION);
    }
}
