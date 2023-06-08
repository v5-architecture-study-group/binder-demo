package org.example.demo.binder.data;

import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

public final class IBAN implements Serializable {

    public static final int MAX_LENGTH = 34;
    private static final Pattern REGEX = Pattern.compile("^[A-Z]{2}[0-9]{2}[0-9|A-Z]{1,30}$");
    private static final String VALID_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String iban;

    public IBAN(String iban) {
        this.iban = validate(iban);
    }

    public static IBAN wrap(@Nullable String iban) {
        return iban == null ? null : new IBAN(iban);
    }

    public static String unwrap(@Nullable IBAN iban) {
        return iban == null ? null : iban.value();
    }

    public String value() {
        return iban;
    }

    public static String validate(String iban) {
        Objects.requireNonNull(iban, "IBAN must not be null");
        var stripped = iban.strip();
        // TODO In a real-life application, you should check that the first two letters are a correct country code.
        //  Once you know the country, you should know the length of the IBAN and check that. In this case, to keep
        //  things simple, we're leaving those checks out.

        if (stripped.length() < 4 || stripped.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("IBAN has incorrect length");
        }
        if (!StringUtils.containsOnly(stripped, VALID_CHARS)) {
            throw new IllegalArgumentException("IBAN contains illegal characters");
        }
        if (!REGEX.matcher(stripped).matches()) {
            throw new IllegalArgumentException("IBAN has incorrect format");
        }
        if (!isMod97ChecksumValid(stripped)) {
            throw new IllegalArgumentException("IBAN has incorrect checksum");
        }
        return iban;
    }

    private static boolean isMod97ChecksumValid(String iban) {
        // TODO In a real-life application, this would be properly implemented. To keep things simple, it is left out
        //  now.
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IBAN iban1 = (IBAN) o;
        return Objects.equals(iban, iban1.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban);
    }
}
