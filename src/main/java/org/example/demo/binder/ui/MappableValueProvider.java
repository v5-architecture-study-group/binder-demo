package org.example.demo.binder.ui;

import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.ValueProvider;

import java.util.Optional;

@FunctionalInterface
public interface MappableValueProvider<SOURCE, TARGET> extends ValueProvider<SOURCE, TARGET> {

    default <T2> MappableValueProvider<SOURCE, T2> map(SerializableFunction<TARGET, T2> mappingFunction) {
        return source -> Optional.ofNullable(apply(source)).map(mappingFunction).orElse(null);
    }

    static <SOURCE, TARGET> MappableValueProvider<SOURCE, TARGET> create(ValueProvider<SOURCE, TARGET> valueProvider) {
        return valueProvider::apply;
    }
}
