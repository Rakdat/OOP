package ru.nsu.tokarev4;

import ru.nsu.tokarev4.model.Element;

/**
 * Класс, представляющий блок кода в формате Markdown.
 * Блоки кода обрамляются тройными обратными апострофами и могут содержать указание языка программирования
 * для подсветки синтаксиса.
 */
public class CodeBlock extends Element {
    private final String language;
    private final String code;

    /**
     * Создает блок кода с указанным языком и содержимым.
     * @param language язык программирования (может быть пустой строкой)
     * @param code содержимое блока кода
     */
    CodeBlock(String language, String code) {
        this.language = language;
        this.code = code;
    }

    /**
     * Сериализует блок кода в строку Markdown.
     * Формат: три обратных апострофа, язык (если указан), перевод строки,
     * код, перевод строки, три обратных апострофа.
     * @return строковое представление блока кода в формате Markdown
     */
    @Override
    public String serialize() {
        return "```" + language + "\n" + code + "\n```";
    }

    /**
     * Встроенный класс Builder для создания объектов CodeBlock.
     * Реализует паттерн Строитель (Builder) для удобного конструирования блоков кода.
     */
    public static class Builder {
        private String language = "";
        private String code;

        /**
         * Устанавливает язык программирования для блока кода.
         */
        public Builder withLanguage(String language) {
            this.language = language;
            return this;
        }

        /**
         * Устанавливает содержимое блока кода.
         */
        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        /**
         * Создает объект CodeBlock на основе заданных параметров.
         * Проверяет, что содержимое кода не равно null
         * @return новый объект CodeBlock
         * @throws IllegalStateException если code равен null
         */
        public CodeBlock build() {
            if (code == null) {
                throw new IllegalStateException("Code cannot be null");
            }
            return new CodeBlock(language, code);
        }
    }
}
