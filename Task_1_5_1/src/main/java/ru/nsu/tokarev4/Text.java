package ru.nsu.tokarev4;

/**
 * Классы для работы с текстом и его форматированием в Markdown.
 */
public class Text extends Element {
    protected String content;
    /**
     * Создает новый текстовый элемент.
     */
    public Text(String content) {
        this.content = content;
    }

    @Override
    public String serialize() {
        return content;
    }

    /**
     * Жирный текст
     */
    public static class Bold extends Text {
        public Bold(String content) {
            super(content);
        }

        @Override
        public String serialize() {
            return "**" + content + "**";
        }
    }

    /**
     * Курсивный текст
     */
    public static class Italic extends Text {
        public Italic(String content) {
            super(content);
        }

        @Override
        public String serialize() {
            return "*" + content + "*";
        }
    }

    /**
     * Зачеркнутый текст
     */
    public static class Strikethrough extends Text {
        public Strikethrough(String content) {
            super(content);
        }

        @Override
        public String serialize() {
            return "~~" + content + "~~";
        }
    }

    /**
     * Встроенный код
     */
    public static class InlineCode extends Text {
        public InlineCode(String content) {
            super(content);
        }

        @Override
        public String serialize() {
            return "`" + content + "`";
        }
    }
}