package org.example.demo.binder.service;

import jakarta.annotation.Nullable;
import org.example.demo.binder.data.*;

import java.util.Objects;

public record CreatePersonCommand(
        PersonName firstName,
        PersonName lastName,
        PersonalIdentityCode pic,
        @Nullable PhoneNumber phoneNumber,
        @Nullable EmailAddress emailAddress,
        @Nullable IBAN bankAccount) {

    public CreatePersonCommand {
        Objects.requireNonNull(firstName, "firstName must not be null");
        Objects.requireNonNull(lastName, "lastName must not be null");
        Objects.requireNonNull(pic, "pic must not be null");
    }
}
