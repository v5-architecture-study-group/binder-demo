package org.example.demo.binder.data;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PersonNameAttributeConverter implements AttributeConverter<PersonName, String> {
    @Override
    public String convertToDatabaseColumn(PersonName attribute) {
        return PersonName.unwrap(attribute);
    }

    @Override
    public PersonName convertToEntityAttribute(String dbData) {
        return PersonName.wrap(dbData);
    }
}
