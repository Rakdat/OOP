package ru.nsu.tokarev4;

/**
 * Класс, представляющий заголовок в формате Markdown.
 * Заголовки в Markdown создаются с помощью символов '#' в начале строки,
 * где количество символов определяет уровень заголовка (от 1 до 6).
 */
public class Header extends Element {
    private final int level;
    private final String text;

    /**
     * Создает заголовок с указанным уровнем и текстом.
     * Уровень автоматически ограничивается диапазоном 1-6.
     * @param level уровень заголовка (будет приведен к диапазону 1-6)
     * @param text текст заголовка
     */
    Header(int level, String text){
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
        public Header build() {
            if (text == null || text.isEmpty()) {
                throw new IllegalStateException("Header text cannot be empty");
            }
            return new Header(level, text);
        }
    }
}