package org.example.demo.binder.data;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class IBANAttributeConverter implements AttributeConverter<IBAN, String> {
    @Override
    public String convertToDatabaseColumn(IBAN attribute) {
        return IBAN.unwrap(attribute);
    }

    @Override
    public IBAN convertToEntityAttribute(String dbData) {
        return IBAN.wrap(dbData);
    }
}
