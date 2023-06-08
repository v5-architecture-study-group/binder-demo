package org.example.demo.binder.data;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Objects;

@Entity
public class Person extends AbstractPersistable<Long> {

    @Column(length = PersonalIdentityCode.LENGTH, nullable = false, unique = true)
    private String personalIdentityCode; // We have to use a String here because attribute converters read the value more than once and PersonalIdentityCode is a read-once type.

    @Column(length = PersonName.MAX_LENGTH, nullable = false)
    @Convert(converter = PersonNameAttributeConverter.class)
    private PersonName firstName;

    @Column(length = PersonName.MAX_LENGTH, nullable = false)
    @Convert(converter = PersonNameAttributeConverter.class)
    private PersonName lastName;

    @Column(length = PhoneNumber.MAX_LENGTH)
    @Convert(converter = PhoneNumberAttributeConverter.class)
    private PhoneNumber phoneNumber;

    @Column(length = EmailAddress.MAX_LENGTH)
    @Convert(converter = EmailAddressAttributeConverter.class)
    private EmailAddress emailAddress;

    @Column(length = IBAN.MAX_LENGTH)
    @Convert(converter = IBANAttributeConverter.class)
    private IBAN bankAccount;

    protected Person() {
    }

    public Person(PersonalIdentityCode personalIdentityCode,
                  PersonName firstName,
                  PersonName lastName,
                  @Nullable PhoneNumber phoneNumber,
                  @Nullable EmailAddress emailAddress,
                  @Nullable IBAN bankAccount) {
        this.personalIdentityCode = Objects.requireNonNull(personalIdentityCode, "personalIdentityCode must not be null").value();
        this.firstName = Objects.requireNonNull(firstName, "firstName must not be null");
        this.lastName = Objects.requireNonNull(lastName, "lastName must not be null");
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.bankAccount = bankAccount;
    }

    public PersonId id() {
        if (getId() == null) {
            throw new IllegalStateException("Person has not been persisted yet");
        }
        return new PersonId(getId());
    }

    public PersonalIdentityCode personalIdentityCode() {
        return new PersonalIdentityCode(personalIdentityCode);
    }

    public PersonName firstName() {
        return firstName;
    }

    public void setFirstName(PersonName firstName) {
        this.firstName = Objects.requireNonNull(firstName, "firstName must not be null");
    }

    public PersonName lastName() {
        return lastName;
    }

    public void setLastName(PersonName lastName) {
        this.lastName = Objects.requireNonNull(lastName, "lastName must not be null");
    }

    public @Nullable PhoneNumber phoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Nullable PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @Nullable EmailAddress emailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(@Nullable EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public @Nullable IBAN bankAccount() {
        return bankAccount;
    }

    public void setBankAccount(@Nullable IBAN bankAccount) {
        this.bankAccount = bankAccount;
    }
}
