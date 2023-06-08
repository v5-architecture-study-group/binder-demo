package org.example.demo.binder.data;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public final class PersonalIdentityCode implements Serializable {

    private static final char[] CONTROL_CHARS = {
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'H',
            'J',
            'K',
            'L',
            'M',
            'N',
            'P',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y'
    };
    private static final Pattern REGEX = Pattern.compile("^[0-9]{6}[+\\-A][0-9]{3}[0-9|A-Y]$");

    public static final int LENGTH = 11;
    private final AtomicReference<String> pic;

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        pic.set(null);
        out.defaultWriteObject();
    }

    public PersonalIdentityCode(String pic) {
        this.pic = new AtomicReference<>(validate(pic));
    }

    public static String validate(String personNumber) {
        Objects.requireNonNull(personNumber, "PersonNumber must not be null");
        var stripped = personNumber.strip();
        if (stripped.length() != LENGTH) {
            throw new IllegalArgumentException("PersonNumber has incorrect length");
        }
        if (!StringUtils.containsOnly(stripped, "+-0123456789ABCDEFHJKLMNPRSTUVWXY")) {
            throw new IllegalArgumentException("PersonNumber contains illegal characters");
        }
        if (!REGEX.matcher(stripped).matches()) {
            throw new IllegalArgumentException("PersonNumber has invalid format");
        }
        var actualControlChar = stripped.charAt(10);
        var expectedControlChar = calculateControlChar(stripped.substring(0, 6), stripped.substring(7, 10));
        if (actualControlChar != expectedControlChar) {
            throw new IllegalArgumentException("PersonNumber has incorrect control char");
        }
        return stripped;
    }

    public String value() {
        return Optional
                .ofNullable(pic.getAndSet(null))
                .orElseThrow(() -> new IllegalStateException("Personal identity code already read"));
    }

    public static PersonalIdentityCode create(LocalDate birthDate, int identityNumber) {
        var separatorChar = getSeparatorChar(birthDate.getYear());
        if (identityNumber < 2 || identityNumber > 899) {
            throw new IllegalArgumentException("Invalid identity number");
        }
        var birthDatePart = "%02d%02d%02d".formatted(
                birthDate.getDayOfMonth(),
                birthDate.getMonthValue(),
                birthDate.getYear() % 100);

        var identityNumberPart = "%03d".formatted(identityNumber);
        var controlChar = calculateControlChar(birthDatePart, identityNumberPart);

        return new PersonalIdentityCode("%s%s%s%s".formatted(birthDatePart, separatorChar, identityNumberPart, controlChar));
    }

    private static char getSeparatorChar(int year) {
        if (year < 1800) {
            throw new IllegalArgumentException("Birth dates before 1800 are not supported");
        }
        if (year > 2099) {
            throw new IllegalArgumentException("Birth dates after 2099 are not supported");
        }
        if (year < 1900) {
            return '+';
        } else if (year < 2000) {
            return '-';
        } else {
            return 'A';
        }
    }

    private static char calculateControlChar(String birthDate, String identityNumber) {
        var number = new BigDecimal(birthDate + identityNumber).setScale(7, RoundingMode.HALF_UP);
        var fraction = number.divide(new BigDecimal(31), RoundingMode.HALF_UP).remainder(BigDecimal.ONE);
        var controlNumber = fraction.multiply(new BigDecimal(31)).setScale(0, RoundingMode.HALF_UP);
        return CONTROL_CHARS[controlNumber.intValue()];
    }
}
