package ru.nsu.tokarev4;


import org.junit.jupiter.api.Test;
import ru.nsu.tokarev4.markdown.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Комплексные unit-тесты для библиотеки Markdown.
 * Проверяет корректность работы всех элементов и паттерна Builder.
 */
public class MarkdownTests {

    // ========== ТЕСТЫ ДЛЯ ТЕКСТА И ФОРМАТИРОВАНИЯ ==========

    @Test
    public void maintest() {
        Main.main(new String[]{});
    }
    @Test
    public void testBoldText() {
        // Проверяем жирное форматирование
        TextMd.BoldMd boldMd = new TextMd.BoldMd("Important");
        assertEquals("**Important**", boldMd.serialize());
    }

    @Test
    public void testItalicText() {
        // Проверяем курсивное форматирование
        TextMd.ItalicMd italicMd = new TextMd.ItalicMd("Emphasis");
        assertEquals("*Emphasis*", italicMd.serialize());
    }

    @Test
    public void testStrikethroughText() {
        // Проверяем зачеркнутый текст
        TextMd.StrikethroughMd strike = new TextMd.StrikethroughMd("Old Price");
        assertEquals("~~Old Price~~", strike.serialize());
    }

    @Test
    public void testInlineCode() {
        // Проверяем встроенный код
        TextMd.InlineCodeMd code = new TextMd.InlineCodeMd("var x = 5");
        assertEquals("`var x = 5`", code.serialize());
    }

    @Test
    public void testTextEquality() {
        // Проверяем сравнение элементов текста
        TextMd textMd1 = new TextMd("Same");
        TextMd textMd2 = new TextMd("Same");
        TextMd textMd3 = new TextMd("Different");

        assertTrue(textMd1.equals(textMd2));
        assertFalse(textMd1.equals(textMd3));
        assertTrue(textMd1.equals(textMd1)); // рефлексивность
    }

    // ========== ТЕСТЫ ДЛЯ ССЫЛОК И ИЗОБРАЖЕНИЙ ==========

    @Test
    public void testLinkBuilder() {
        // Проверяем создание ссылки через Builder
        LinkMd linkMd = new LinkMd.Builder()
                .withText("Google")
                .withUrl("https://google.com")
                .build();

        assertEquals("[Google](https://google.com)", linkMd.serialize());
    }

    @Test
    public void testImageBuilder() {
        // Проверяем создание изображения через Builder
        ImageMd imageMd = new ImageMd.Builder()
                .withAltText("Logo")
                .withUrl("/images/logo.png")
                .build();

        assertEquals("![Logo](/images/logo.png)", imageMd.serialize());
    }

    // ========== ТЕСТЫ ДЛЯ ЗАГОЛОВКОВ ==========

    @Test
    public void testHeaderBuilder() {
        // Проверяем создание заголовков разных уровней
        HeaderMd h1 = new HeaderMd.Builder()
                .withLevel(1)
                .withText("Main Title")
                .build();

        HeaderMd h3 = new HeaderMd.Builder()
                .withLevel(3)
                .withText("Subtitle")
                .build();

        assertEquals("# Main Title", h1.serialize());
        assertEquals("### Subtitle", h3.serialize());
    }

    @Test
    public void testHeaderLevelLimits() {
        // Проверяем ограничение уровней заголовков (1-6)
        HeaderMd tooHigh = new HeaderMd.Builder()
                .withLevel(10)
                .withText("Test")
                .build();

        HeaderMd tooLow = new HeaderMd.Builder()
                .withLevel(0)
                .withText("Test")
                .build();

        assertEquals("###### Test", tooHigh.serialize()); // Ограничено до 6
        assertEquals("# Test", tooLow.serialize());      // Ограничено до 1
    }

    // ========== ТЕСТЫ ДЛЯ ЦИТАТ ==========

    @Test
    public void testBlockquoteBuilder() {
        // Проверяем создание цитат с несколькими элементами
        BlockquoteMd quote = new BlockquoteMd.Builder()
                .addElement(new TextMd("First line"))
                .addElement(new TextMd("Second line"))
                .build();

        String expected = "> First line\n> Second line";
        assertEquals(expected, quote.serialize());
    }

    // ========== ТЕСТЫ ДЛЯ БЛОКОВ КОДА ==========

    @Test
    public void testCodeBlockBuilder() {
        // Проверяем создание блоков кода с указанием языка
        CodeBlockMd code = new CodeBlockMd.Builder()
                .withLanguage("java")
                .withCode("public class Test {\n    // code\n}")
                .build();

        String expected = "```java\npublic class Test {\n    // code\n}\n```";
        assertEquals(expected, code.serialize());
    }

    @Test
    public void testCodeBlockWithoutLanguage() {
        // Проверяем блок кода без указания языка
        CodeBlockMd code = new CodeBlockMd.Builder()
                .withCode("plain text")
                .build();

        assertEquals("```\nplain text\n```", code.serialize());
    }

    // ========== ТЕСТЫ ДЛЯ СПИСКОВ ==========

    @Test
    public void testUnorderedList() {
        // Проверяем неупорядоченный список
        ListElementMd list = new ListElementMd.Builder()
                .withOrdered(false)
                .addItem(new TextMd("First"))
                .addItem(new TextMd("Second"))
                .build();

        String expected = "- First\n- Second\n";
        assertEquals(expected, list.serialize());
    }

    @Test
    public void testOrderedList() {
        // Проверяем упорядоченный список
        ListElementMd list = new ListElementMd.Builder()
                .withOrdered(true)
                .addItem(new TextMd("First"))
                .addItem(new TextMd("Second"))
                .build();

        String expected = "1. First\n2. Second\n";
        assertEquals(expected, list.serialize());
    }

    @Test
    public void testNestedList() {
        // Проверяем вложенные списки
        ListElementMd list = new ListElementMd.Builder()
                .withOrdered(false)
                .addItem(new TextMd("Level 1"))
                .addItem(new TextMd("Level 2"), 1)
                .addItem(new TextMd("Level 3"), 2)
                .build();

        String expected = "- Level 1\n  - Level 2\n    - Level 3\n";
        assertEquals(expected, list.serialize());
    }

    // ========== ТЕСТЫ ДЛЯ ЗАДАЧ ==========

    @Test
    public void testTaskBuilder() {
        // Проверяем создание задач (чекбоксов)
        TaskMd taskMd1 = new TaskMd.Builder()
                .withText("Completed task")
                .withChecked(true)
                .build();

        TaskMd taskMd2 = new TaskMd.Builder()
                .withText("Pending task")
                .withChecked(false)
                .build();

        assertEquals("- [x] Completed task", taskMd1.serialize());
        assertEquals("- [ ] Pending task", taskMd2.serialize());
    }

    // ========== ТЕСТЫ ДЛЯ ТАБЛИЦ ==========

    @Test
    public void testTableBuilderBasic() {
        // Проверяем создание простой таблицы
        TableMd tableMd = new TableMd.Builder()
                .addRow("Name", "Age")
                .addRow("Alice", 25)
                .addRow("Bob", 30)
                .build();

        assertTrue(tableMd.serialize().contains("| Name  | Age |"));
        assertTrue(tableMd.serialize().contains("| Alice | 25  |"));
        assertTrue(tableMd.serialize().contains("| Bob   | 30  |"));
    }

    @Test
    public void testTableWithRowLimit() {
        // Проверяем ограничение количества строк
        TableMd tableMd = new TableMd.Builder()
                .withRowLimit(2) // Заголовок + 1 строка данных
                .addRow("A", "B")
                .addRow(1, 2)
                .addRow(3, 4) // Эта строка не должна отобразиться
                .build();

        String result = tableMd.serialize();
        assertTrue(result.contains("| 1 | 2 |"));
        assertFalse(result.contains("| 3 | 4 |"));
    }

    @Test
    public void testTableWithFormattedCells() {
        // Проверяем таблицу с форматированными ячейками
        TableMd tableMd = new TableMd.Builder()
                .addRow("Product", "Price")
                .addRow("Apple", new TextMd.BoldMd("$1.99"))
                .addRow("Banana", new TextMd.ItalicMd("$0.99"))
                .build();

        String result = tableMd.serialize();
        assertTrue(result.contains("**$1.99**"));
        assertTrue(result.contains("*$0.99*"));
    }
}