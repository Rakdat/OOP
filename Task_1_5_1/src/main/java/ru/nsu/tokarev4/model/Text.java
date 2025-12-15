package ru.nsu.tokarev4.model;

/**
 * Интерфейс для текстовых элементов Markdown.
 * Определяет иерархию интерфейсов для различных видов текстового форматирования.
 */
public interface Text extends Element {

    /**
     * Интерфейс для жирного текста.
     */
    interface Bold extends Text {
    }

    /**
     * Интерфейс для курсивного текста.
     */
    interface Italic extends Text {
    }

    /**
     * Интерфейс для зачеркнутого текста.
     */
    interface Strikethrough extends Text {
    }

    /**
     * Интерфейс для встроенного кода.
     */
    interface InlineCodeMd extends Text {
    }
}
