package org.example.demo.binder.data;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EmailAddressAttributeConverter implements AttributeConverter<EmailAddress, String> {
    @Override
    public String convertToDatabaseColumn(EmailAddress attribute) {
        return EmailAddress.unwrap(attribute);
    }

    @Override
    public EmailAddress convertToEntityAttribute(String dbData) {
        return EmailAddress.wrap(dbData);
    }
}
