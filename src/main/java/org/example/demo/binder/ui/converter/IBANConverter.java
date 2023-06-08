package org.example.demo.binder.ui.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import org.example.demo.binder.data.IBAN;

public final class IBANConverter implements Converter<String, IBAN> {

    public static final IBANConverter INSTANCE = new IBANConverter();

    @Override
    public Result<IBAN> convertToModel(String value, ValueContext context) {
        try {
            return Result.ok(IBAN.wrap(value));
        } catch (IllegalArgumentException ex) {
            return Result.error(ex.getMessage());
        }
    }

    @Override
    public String convertToPresentation(IBAN value, ValueContext context) {
        return IBAN.unwrap(value);
    }
}
