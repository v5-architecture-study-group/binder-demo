package org.example.demo.binder.ui.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import org.example.demo.binder.data.PhoneNumber;

public final class PhoneNumberConverter implements Converter<String, PhoneNumber> {

    public static final PhoneNumberConverter INSTANCE = new PhoneNumberConverter();

    @Override
    public Result<PhoneNumber> convertToModel(String value, ValueContext context) {
        try {
            return Result.ok(PhoneNumber.wrap(value));
        } catch (IllegalArgumentException ex) {
            return Result.error(ex.getMessage());
        }
    }

    @Override
    public String convertToPresentation(PhoneNumber value, ValueContext context) {
        return PhoneNumber.unwrap(value);
    }
}
