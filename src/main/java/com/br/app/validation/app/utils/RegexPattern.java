package com.br.app.validation.app.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegexPattern {

    public static boolean containsLowerCase(final String value) {
        final var pattern = Pattern.compile("\\p{Lower}+");
        final var matcher = pattern.matcher(value);
        return matcher.find();
    }

    public static boolean containsDigit(final String value) {
        final var pattern = Pattern.compile("\\p{Digit}+");
        final var matcher = pattern.matcher(value);
        return matcher.find();
    }

    public static boolean containsSpecialCharacter(final String value) {
        final var pattern = Pattern.compile("\\p{Punct}+");
        final var matcher = pattern.matcher(value);
        return matcher.find();
    }

    public static boolean containsUpperCase(final String value) {
        final var pattern = Pattern.compile("\\p{Upper}+");
        final var matcher = pattern.matcher(value);
        return matcher.find();
    }
}
