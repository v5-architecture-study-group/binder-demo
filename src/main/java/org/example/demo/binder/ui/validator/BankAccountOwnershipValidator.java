package org.example.demo.binder.ui.validator;

import com.vaadin.flow.data.binder.ErrorLevel;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import org.example.demo.binder.data.IBAN;
import org.example.demo.binder.data.PersonId;
import org.example.demo.binder.service.PersonService;

import java.util.Objects;

public final class BankAccountOwnershipValidator implements Validator<IBAN> {
    private final PersonId personId;
    private final PersonService personService;

    public BankAccountOwnershipValidator(PersonService personService, PersonId personId) {
        this.personId = Objects.requireNonNull(personId, "personId must not be null");
        this.personService = Objects.requireNonNull(personService, "personService must not be null");
    }

    @Override
    public ValidationResult apply(IBAN value, ValueContext context) {
        if (value == null || personService.isBankAccountOwnedByPerson(personId, value)) {
            return ValidationResult.ok();
        } else {
            return ValidationResult.create("This bank account number is not owned by this person", ErrorLevel.WARNING);
        }
    }
}
