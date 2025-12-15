package ru.nsu.tokarev4;

import ru.nsu.tokarev4.model.Element;

/**
 * Класс, представляющий гиперссылку в формате Markdown.
 * Формат: [текст ссылки](URL)
 * Использует паттерн Builder для создания ссылок.
 */
public class Link extends Element {
    private final String text;
    private final String url;

    /**
     * Приватный конструктор.
     * @param text отображаемый текст ссылки
     * @param url URL адрес
     */
    Link(String text, String url) {
        this.text = text;
        this.url = url;
    }

    @Override
    public String serialize() {
        return "[" + text + "](" + url + ")";
    }

    /**
     * Builder для пошагового создания ссылок.
     */
    public static class Builder {
        private String text;
        private String url;

        /**
         * Устанавливает отображаемый текст ссылки.
         */
        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        /**
         * Устанавливает URL адрес ссылки.
         */
        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }


        /**
         * Создает объект Link.
         * @return созданная ссылка
         * @throws IllegalStateException если не указан текст или URL
         */
        public Link build() {
            if (text == null || url == null) {
                throw new IllegalStateException("Text and URL must be specified for Link");
            }
            return new Link(text, url);
        }
    }
}