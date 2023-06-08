package org.example.demo.binder.data;

import java.io.Serializable;
import java.util.Objects;

public final class PersonId implements Serializable {

    private final long id;

    public PersonId(long id) {
        this.id = id;
    }

    public long toLong() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonId personId = (PersonId) o;
        return id == personId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
