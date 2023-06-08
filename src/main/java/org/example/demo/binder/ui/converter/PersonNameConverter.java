package org.example.demo.binder.ui.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import org.example.demo.binder.data.PersonName;

public final class PersonNameConverter implements Converter<String, PersonName> {

    public static final PersonNameConverter INSTANCE = new PersonNameConverter();

    @Override
    public Result<PersonName> convertToModel(String s, ValueContext valueContext) {
        try {
            return Result.ok(PersonName.wrap(s));
        } catch (IllegalArgumentException ex) {
            return Result.error(ex.getMessage());
        }
    }

    @Override
    public String convertToPresentation(PersonName personName, ValueContext valueContext) {
        return PersonName.unwrap(personName);
    }
}
