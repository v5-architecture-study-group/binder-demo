package org.example.demo.binder.service;

import jakarta.annotation.Nullable;
import org.example.demo.binder.data.*;

import java.util.Objects;

public record UpdatePersonCommand(
        PersonId personId,
        PersonName firstName,
        PersonName lastName,
        @Nullable PhoneNumber phoneNumber,
        @Nullable EmailAddress emailAddress,
        @Nullable IBAN bankAccount
) {

    public UpdatePersonCommand {
        Objects.requireNonNull(personId, "personId must not be null");
        Objects.requireNonNull(firstName, "firstName must not be null");
        Objects.requireNonNull(lastName, "lastName must not be null");
    }
}
