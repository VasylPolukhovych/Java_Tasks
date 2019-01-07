package common;

import java.util.Objects;

public class Identifier {
    static private Integer nextId = 0;
    private int id = 1;

    public Identifier() {
        setIdToNextValue();
    }

    private void setIdToNextValue() {
        nextId = nextId + 1;
        id = nextId;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identifier)) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
