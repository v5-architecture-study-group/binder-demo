package org.example.demo.binder.data;

import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

public final class PhoneNumber implements Serializable {

    public static final int MAX_LENGTH = 15;
    private final String phoneNumber;

    public PhoneNumber(String phoneNumber) {
        this.phoneNumber = validate(phoneNumber);
    }

    public static PhoneNumber wrap(@Nullable String phoneNumber) {
        return phoneNumber == null ? null : new PhoneNumber(phoneNumber);
    }

    public static String unwrap(@Nullable PhoneNumber phoneNumber) {
        return phoneNumber == null ? null : phoneNumber.value();
    }

    public String value() {
        return phoneNumber;
    }

    private static String validate(String phoneNumber) {
        Objects.requireNonNull(phoneNumber, "Phone number must not be null");
        var stripped = phoneNumber.strip();
        if (stripped.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Phone number is too long");
        }
        if (stripped.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        if (!StringUtils.isAsciiPrintable(phoneNumber)) {
            throw new IllegalArgumentException("Phone number can only contain ASCII characters");
        }
        if (stripped.charAt(0) == '+') {
            if (!StringUtils.isNumeric(stripped.substring(1))) {
                throw new IllegalArgumentException("Phone number can only contain digits after +");
            }
        } else if (!StringUtils.isNumeric(stripped)) {
            throw new IllegalArgumentException("Phone number can only contain digits");
        }
        return stripped;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }
}
