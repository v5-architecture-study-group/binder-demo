package org.example.demo.binder.ui.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import org.example.demo.binder.data.PersonalIdentityCode;
import org.example.demo.binder.service.PersonService;

public class PersonalIdentityCodeUniquenessValidator implements Validator<String> {

    private final PersonService personService;

    public PersonalIdentityCodeUniquenessValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        if (value != null && personService.personalIdentityCodeExists(new PersonalIdentityCode(value))) {
            return ValidationResult.error("Personal identity code already exists");
        } else {
            return ValidationResult.ok();
        }
    }
}
