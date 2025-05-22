package chloe.movietalk.domain.enums;

import chloe.movietalk.exception.global.InvalidEnumValueException;

import java.util.Arrays;

public enum Gender {
    MALE,
    FEMALE,
    OTHER;

    public static Gender from(String genderStr) {
        if (genderStr == null)
            return null;
        return Arrays.stream(values())
                .filter(g -> g.name().equals(genderStr))
                .findFirst()
                .orElseThrow(() -> InvalidEnumValueException.EXCEPTION);
    }
}
