package chloe.movietalk.domain;

import chloe.movietalk.exception.InvalidEnumValueException;

import java.util.Arrays;

public enum Gender {
    MALE,
    FEMALE,
    OTHER;

    public static Gender from(String genderStr) {
        System.out.println(genderStr);
        return Arrays.stream(values())
                .filter(g -> g.name().equals(genderStr))
                .findFirst()
                .orElseThrow(() -> InvalidEnumValueException.EXCEPTION);
    }
}
