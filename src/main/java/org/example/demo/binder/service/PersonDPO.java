package org.example.demo.binder.service;

import org.example.demo.binder.data.*;

import java.io.Serializable;

public record PersonDPO(
        PersonId id,
        PersonName firstName,
        PersonName lastName,
        PhoneNumber phoneNumber,
        EmailAddress emailAddress,
        IBAN bankAccount
) implements Serializable {
}
