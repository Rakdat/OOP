package ru.nsu.tokarev4.markdown;

import ru.nsu.tokarev4.model.AbstractElement;
import ru.nsu.tokarev4.model.Element;
import ru.nsu.tokarev4.model.Link;

/**
 * Класс, представляющий гиперссылку в формате Markdown.
 * Формат: [текст ссылки](URL)
 * Использует паттерн Builder для создания ссылок.
 */
public class LinkMd extends AbstractElement implements Link {
    private final String text;
    private final String url;

    /**
     * Приватный конструктор.
     * @param text отображаемый текст ссылки
     * @param url URL адрес
     */
    LinkMd(String text, String url) {
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
        public LinkMd build() {
            if (text == null || url == null) {
                throw new IllegalStateException("Text and URL must be specified for Link");
            }
            return new LinkMd(text, url);
        }
    }
}