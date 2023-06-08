package org.example.demo.binder.data;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PhoneNumberAttributeConverter implements AttributeConverter<PhoneNumber, String> {
    @Override
    public String convertToDatabaseColumn(PhoneNumber attribute) {
        return PhoneNumber.unwrap(attribute);
    }

    @Override
    public PhoneNumber convertToEntityAttribute(String dbData) {
        return PhoneNumber.wrap(dbData);
    }
}
