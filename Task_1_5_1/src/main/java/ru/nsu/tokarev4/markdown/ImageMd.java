package ru.nsu.tokarev4.markdown;

import ru.nsu.tokarev4.model.AbstractElement;
import ru.nsu.tokarev4.model.Element;
import ru.nsu.tokarev4.model.Image;

/**
 * Класс, представляющий изображение в формате Markdown.
 * Формат изображений аналогичен формату ссылок, но с восклицательным знаком в начале.
 */
public class ImageMd extends AbstractElement implements Image {
    private final String altText;
    private final String url;

    /**
     * Создает изображение с указанным альтернативным текстом и URL.
     * @param altText альтернативный текст (отображается, если изображение не загружено)
     * @param url URL адрес изображения
     */
    ImageMd(String altText, String url) {
        this.altText = altText;
        this.url = url;
    }

    @Override
    public String serialize() {
        return "![" + altText + "](" + url + ")";
    }

    /**
     * Встроенный класс Builder для создания объектов Image.
     * Реализует паттерн Builder для удобного конструирования изображений.
     */
    public static class Builder {
        private String altText;
        private String url;

        /**
         * Устанавливает альтернативный текст изображения.
         */
        public Builder withAltText(String altText) {
            this.altText = altText;
            return this;
        }

        /**
         * Устанавливает URL адрес изображения.
         */
        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * Создает объект Image на основе заданных параметров.
         * Проверяет, что альтернативный текст и URL не равны null.
         */
        public ImageMd build() {
            if (altText == null || url == null) {
                throw new IllegalStateException("Alt text and URL must be specified for Image");
            }
            return new ImageMd(altText, url);
        }
    }
}