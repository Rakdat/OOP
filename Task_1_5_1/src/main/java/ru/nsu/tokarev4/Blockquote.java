package ru.nsu.tokarev4;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий цитату (блочную цитату) в формате Markdown.
 * Цитаты в Markdown создаются с помощью символа '>' в начале каждой строки.
 * Может содержать несколько вложенных элементов.
 */
public class Blockquote extends Element {
    /** Список элементов, составляющих цитату */
    private final List<Element> elements = new ArrayList<>();

    private Blockquote() {}

    /**
     * Добавляет элемент в цитату.
     * Используется внутренне Builder'ом.
     */
    Blockquote addElement(Element element) {
        elements.add(element);
        return this;
    }

    /**
     * Сериализует цитату в строку Markdown.
     * Каждый элемент разбивается на строки и каждая строка получает префикс '> '.
     * @return строковое представление цитаты в формате Markdown
     */
    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        for (Element element : elements) {
            String[] lines = element.serialize().split("\n");
            for (String line : lines) {
                sb.append("> ").append(line).append("\n");
            }
        }
        return sb.toString().trim();
    }

    /**
     * Встроенный класс Builder для создания объектов Blockquote.
     * Реализует паттерн Builder для удобного конструирования цитат.
     */
    public static class Builder {
        private final List<Element> elements = new ArrayList<>();

        /**
         * Добавляет элемент в цитату.
         */
        public Builder addElement(Element element) {
            elements.add(element);
            return this;
        }

        /**
         * Создает объект Blockquote на основе добавленных элементов.
         */
        public Blockquote build() {
            Blockquote blockquote = new Blockquote();
            for (Element element : elements) {
                blockquote.addElement(element);
            }
            return blockquote;
        }
    }
}
