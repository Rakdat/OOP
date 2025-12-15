package ru.nsu.tokarev4.markdown;

import ru.nsu.tokarev4.model.AbstractElement;
import ru.nsu.tokarev4.model.Element;
import ru.nsu.tokarev4.model.Header;

/**
 * Класс, представляющий заголовок в формате Markdown.
 * Заголовки в Markdown создаются с помощью символов '#' в начале строки,
 * где количество символов определяет уровень заголовка (от 1 до 6).
 */
public class HeaderMd extends AbstractElement implements Header {
    private final int level;
    private final String text;

    /**
     * Создает заголовок с указанным уровнем и текстом.
     * Уровень автоматически ограничивается диапазоном 1-6.
     * @param level уровень заголовка (будет приведен к диапазону 1-6)
     * @param text текст заголовка
     */
    HeaderMd(int level, String text){
        this.level = Math.max(1, Math.min(6, level));
        this.text = text;
    }

    @Override
    public String serialize() {
        return "#".repeat(level) + " " + text;
    }

    /**
     * Встроенный класс Builder для создания объектов Header.
     * Реализует паттерн Builder для удобного конструирования заголовков.
     */
    public static class Builder {
        private int level = 1;
        private String text;

        /**
         * Устанавливает уровень заголовка.
         */
        public Builder withLevel(int level) {
            this.level = level;
            return this;
        }

        /**
         * Устанавливает текст заголовка.
         */
        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        /**
         * Создает объект Header на основе заданных параметров.
         * Проверяет, что текст заголовка не пустой.
         * @return новый объект Header
         * @throws IllegalStateException если текст равен null или пустой строке
         */
        public HeaderMd build() {
            if (text == null || text.isEmpty()) {
                throw new IllegalStateException("Header text cannot be empty");
            }
            return new HeaderMd(level, text);
        }
    }
}