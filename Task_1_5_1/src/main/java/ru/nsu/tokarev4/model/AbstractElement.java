package ru.nsu.tokarev4.model;

public abstract class AbstractElement implements Element {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Element)) {
            return false;
        }
        return serialize().equals(((Element) obj).serialize());
    }

    @Override
    public String toString() {
        return serialize();
    }

}
