package org.example.demo.binder.ui.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.function.SerializableSupplier;
import jakarta.annotation.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public final class DelegatingValidator<T> implements Validator<T> {

    private SerializableSupplier<Validator<T>> delegateSupplier;

    public static <T> DelegatingValidator<T> fromSupplier(SerializableSupplier<Validator<T>> delegateSupplier) {
        Objects.requireNonNull(delegateSupplier, "delegateSupplier must not be null");
        return new DelegatingValidator<T>().setDelegateSupplier(delegateSupplier);
    }

    public DelegatingValidator<T> setDelegate(@Nullable Validator<T> delegate) {
        this.delegateSupplier = () -> delegate;
        return this;
    }

    public DelegatingValidator<T> setDelegateSupplier(@Nullable SerializableSupplier<Validator<T>> delegateSupplier) {
        this.delegateSupplier = delegateSupplier;
        return this;
    }

    @Override
    public ValidationResult apply(T value, ValueContext context) {
        return Optional.ofNullable(delegateSupplier)
                .map(Supplier::get)
                .map(validator -> validator.apply(value, context))
                .orElse(ValidationResult.ok());
    }
}
