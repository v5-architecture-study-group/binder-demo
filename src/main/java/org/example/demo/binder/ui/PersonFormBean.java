package org.example.demo.binder.ui;

import jakarta.annotation.Nullable;
import org.example.demo.binder.data.*;
import org.example.demo.binder.service.CreatePersonCommand;
import org.example.demo.binder.service.PersonDPO;
import org.example.demo.binder.service.UpdatePersonCommand;

import java.io.Serializable;

public class PersonFormBean implements Serializable {

    private PersonName firstName;
    private PersonName lastName;
    private PhoneNumber phoneNumber;
    private EmailAddress emailAddress;
    private String pic;
    private IBAN bankAccount;

    public @Nullable PersonName getFirstName() {
        return firstName;
    }

    public void setFirstName(@Nullable PersonName firstName) {
        this.firstName = firstName;
    }

    public @Nullable PersonName getLastName() {
        return lastName;
    }

    public void setLastName(@Nullable PersonName lastName) {
        this.lastName = lastName;
    }

    public @Nullable PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Nullable PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @Nullable EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(@Nullable EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public @Nullable String getPic() {
        return pic;
    }

    public void setPic(@Nullable String pic) {
        this.pic = pic;
    }

    public @Nullable IBAN getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(@Nullable IBAN bankAccount) {
        this.bankAccount = bankAccount;
    }

    public static PersonFormBean fromPersonDPO(PersonDPO personDPO) {
        var bean = new PersonFormBean();
        bean.setFirstName(personDPO.firstName());
        bean.setLastName(personDPO.lastName());
        bean.setPhoneNumber(personDPO.phoneNumber());
        bean.setEmailAddress(personDPO.emailAddress());
        bean.setBankAccount(personDPO.bankAccount());
        return bean;
    }

    public CreatePersonCommand toCreatePersonCommand() {
        return new CreatePersonCommand(firstName, lastName, new PersonalIdentityCode(pic), phoneNumber, emailAddress, bankAccount);
    }

    public UpdatePersonCommand toUpdatePersonCommand(PersonId personId) {
        return new UpdatePersonCommand(personId, firstName, lastName, phoneNumber, emailAddress, bankAccount);
    }
}
