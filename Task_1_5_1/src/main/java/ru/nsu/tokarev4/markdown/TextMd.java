package ru.nsu.tokarev4.markdown;

import ru.nsu.tokarev4.model.AbstractElement;
import ru.nsu.tokarev4.model.Text;

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
    public static class ItalicMd extends TextMd implements Italic {
        public ItalicMd(String content) {
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
    public static class StrikethroughMd extends TextMd implements Strikethrough{
        public StrikethroughMd(String content) {
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
    public static class InlineCodeMd extends TextMd implements Text.InlineCodeMd {
        public InlineCodeMd(String content) {
            super(content);
        }

        @Override
        public String serialize() {
            return "`" + content + "`";
        }
    }
}