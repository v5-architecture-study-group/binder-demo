package org.example.demo.binder.ui.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import org.example.demo.binder.data.PersonalIdentityCode;

public final class PersonalIdentityCodeValidator implements Validator<String> {

    public static final PersonalIdentityCodeValidator INSTANCE = new PersonalIdentityCodeValidator();

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        if (value == null) {
            return ValidationResult.ok();
        }
        try {
            PersonalIdentityCode.validate(value);
            return ValidationResult.ok();
        } catch (IllegalArgumentException ex) {
            return ValidationResult.error(ex.getMessage());
        }
    }
}
