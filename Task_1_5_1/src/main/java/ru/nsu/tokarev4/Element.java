package ru.nsu.tokarev4;

/**
 * Абстрактный базовый класс для всех элементов Markdown.
 * Определяет контракт сериализации и сравнения.
 */
public abstract class Element {

    /**
     * Сериализует элемент в строку Markdown
     * @return Markdown представление элемента
     */
    public abstract String serialize();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return serialize().equals(((Element) obj).serialize());
    }

    @Override
    public String toString() {
        return serialize();
    }
}
