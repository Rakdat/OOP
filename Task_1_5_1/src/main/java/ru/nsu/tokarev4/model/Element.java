package ru.nsu.tokarev4.model;

/**
 * Абстрактный базовый класс для всех элементов Markdown.
 * Определяет контракт сериализации и сравнения.
 */
public interface Element {

    /**
     * Сериализует элемент в строку Markdown
     * @return Markdown представление элемента
     */
    String serialize();
}
