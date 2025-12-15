package ru.nsu.tokarev4.markdown;

import ru.nsu.tokarev4.model.AbstractElement;
import ru.nsu.tokarev4.model.text.Text;

/**
 * Классы для работы с текстом и его форматированием в Markdown.
 */
public class TextMd extends AbstractElement implements Text {
    protected String content;
    /**
     * Создает новый текстовый элемент.
     */
    public TextMd(String content) {
        this.content = content;
    }

    @Override
    public String serialize() {
        return content;
    }

    /**
     * Жирный текст
     */
    public static class BoldMd extends TextMd implements Bold {
        public BoldMd(String content) {
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
    public static class Italic extends TextMd {
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
    public static class Strikethrough extends TextMd {
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
    public static class InlineCode extends TextMd {
        public InlineCode(String content) {
            super(content);
        }

        @Override
        public String serialize() {
            return "`" + content + "`";
        }
    }
}