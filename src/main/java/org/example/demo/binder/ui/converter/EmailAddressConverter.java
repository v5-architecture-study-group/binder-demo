package org.example.demo.binder.ui.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import org.example.demo.binder.data.EmailAddress;

public final class EmailAddressConverter implements Converter<String, EmailAddress> {

    public static final EmailAddressConverter INSTANCE = new EmailAddressConverter();

    @Override
    public Result<EmailAddress> convertToModel(String value, ValueContext context) {
        try {
            return Result.ok(EmailAddress.wrap(value));
        } catch (IllegalArgumentException ex) {
            return Result.error(ex.getMessage());
        }
    }

    @Override
    public String convertToPresentation(EmailAddress value, ValueContext context) {
        return EmailAddress.unwrap(value);
    }
}
