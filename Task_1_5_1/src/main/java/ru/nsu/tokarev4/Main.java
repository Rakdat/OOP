package ru.nsu.tokarev4;

import ru.nsu.tokarev4.markdown.TextMd;

/**
 * Демонстрационный класс, показывающий использование всех элементов библиотеки Markdown.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("ДЕМОНСТРАЦИЯ БИБЛИОТЕКИ MARKDOWN");
        System.out.println("=".repeat(60));

        // Разделитель для лучшей читаемости
        String separator = "\n" + "-".repeat(60) + "\n";

        // 1. ТЕКСТ И ФОРМАТИРОВАНИЕ
        System.out.println("\n1. ТЕКСТ И ФОРМАТИРОВАНИЕ:");
        System.out.println("-".repeat(30));

        TextMd plainTextMd = new TextMd("Обычный текст");
        TextMd.BoldMd boldMdText = new TextMd.BoldMd("Жирный текст");
        TextMd.Italic italicText = new TextMd.Italic("Курсивный текст");
        TextMd.Strikethrough strikeText = new TextMd.Strikethrough("Зачеркнутый текст");
        TextMd.InlineCode codeText = new TextMd.InlineCode("код");

        System.out.println("Обычный: " + plainTextMd);
        System.out.println("Жирный: " + boldMdText);
        System.out.println("Курсив: " + italicText);
        System.out.println("Зачеркнутый: " + strikeText);
        System.out.println("Встроенный код: " + codeText);

        // Комбинированное форматирование
        System.out.println("\nКомбинирование:");
        System.out.println(new TextMd("Текст с " +
                new TextMd.BoldMd("жирным") + " и " +
                new TextMd.Italic("курсивным") + " форматированием"));

        // 2. ЗАГОЛОВКИ
        System.out.println(separator + "2. ЗАГОЛОВКИ (H1-H6):");

        for (int level = 1; level <= 6; level++) {
            Header header = new Header.Builder()
                    .withLevel(level)
                    .withText("Заголовок уровня " + level)
                    .build();
            System.out.println(header);
        }

        // 3. ССЫЛКИ И ИЗОБРАЖЕНИЯ
        System.out.println(separator + "3. ССЫЛКИ И ИЗОБРАЖЕНИЯ:");

        Link link = new Link.Builder()
                .withText("Мой репозиторий ООП")
                .withUrl("https://github.com/Rakdat/OOP")
                .build();
        System.out.println("Ссылка: " + link);

        Image image = new Image.Builder()
                .withAltText("Логотип Markdown")
                .withUrl("/images/markdown-logo.png")
                .build();
        System.out.println("Изображение: " + image);

        // 4. СПИСКИ
        System.out.println(separator + "4. СПИСКИ:");

        System.out.println("Неупорядоченный список:");
        ListElement unorderedList = new ListElement.Builder()
                .withOrdered(false)
                .addItem(new TextMd("Первый элемент"))
                .addItem(new TextMd("Второй элемент с " + new TextMd.BoldMd("жирным текстом")))
                .addItem(new TextMd("Вложенный элемент"), 1)
                .addItem(new TextMd("Еще более вложенный"), 2)
                .addItem(new TextMd("Третий элемент"))
                .build();
        System.out.println(unorderedList);

        System.out.println("Упорядоченный список:");
        ListElement orderedList = new ListElement.Builder()
                .withOrdered(true)
                .addItem(new TextMd("Шаг первый"))
                .addItem(new TextMd("Шаг второй"))
                .addItem(new TextMd("Шаг третий"))
                .build();
        System.out.println(orderedList);

        // 5. ЗАДАЧИ (ЧЕКБОКСЫ)
        System.out.println(separator + "5. ЗАДАЧИ (TODO список):");

        Task completedTask = new Task.Builder()
                .withText("Изучить паттерн Builder")
                .withChecked(true)
                .build();

        Task pendingTask = new Task.Builder()
                .withText("Написать unit-тесты")
                .withChecked(false)
                .build();

        System.out.println(completedTask);
        System.out.println(pendingTask);

        // 6. ЦИТАТЫ
        System.out.println(separator + "6. ЦИТАТЫ:");

        Blockquote quote = new Blockquote.Builder()
                .addElement(new TextMd("Это важная цитата"))
                .addElement(new TextMd("из нескольких строк"))
                .addElement(new TextMd("с " + new TextMd.Italic("курсивным") + " текстом"))
                .build();
        System.out.println(quote);

        // 7. БЛОКИ КОДА
        System.out.println(separator + "7. БЛОКИ КОДА:");

        System.out.println("Блок кода на Java:");
        CodeBlock javaCode = new CodeBlock.Builder()
                .withLanguage("java")
                .withCode("public class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, Markdown!\");\n    }\n}")
                .build();
        System.out.println(javaCode);

        System.out.println("\nБлок кода без указания языка:");
        CodeBlock plainCode = new CodeBlock.Builder()
                .withCode("Это просто текст\nв несколько строк")
                .build();
        System.out.println(plainCode);

        // 8. ТАБЛИЦЫ
        System.out.println(separator + "8. ТАБЛИЦЫ:");

        // Пример из задания (улучшенный)
        System.out.println("Пример из задания:");
        Table.Builder taskTableBuilder = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .withRowLimit(8)
                .addRow("Index", "Random");

        for (int i = 1; i <= 5; i++) {
            final int value = (int) (Math.random() * 10);
            if (value > 5) {
                taskTableBuilder.addRow(i, new TextMd.BoldMd(String.valueOf(value)));
            } else {
                taskTableBuilder.addRow(i, value);
            }
        }
        System.out.println(taskTableBuilder.build());

        // Большая демонстрационная таблица
        System.out.println("\nДемонстрационная таблица со всеми типами форматирования:");
        Table demoTable = new Table.Builder()
                .withAlignments(Table.ALIGN_CENTER, Table.ALIGN_CENTER,
                        Table.ALIGN_CENTER, Table.ALIGN_CENTER)
                .addRow("Тип","Результат", "Исходный код")
                .addRow("Жирный",
                        new TextMd.BoldMd("текст"),
                        "new Text.Bold(\"текст\")")
                .addRow("Курсив",
                        new TextMd.Italic("текст"),
                        "new Text.Italic(\"текст\")")
                .addRow("Зачеркнутый",
                        new TextMd.Strikethrough("текст"),
                        "new Text.Strikethrough(\"текст\")")
                .addRow("Код",
                        new TextMd.InlineCode("код"),
                        "new Text.InlineCode(\"код\")")
                .addRow("Ссылка",
                        new Link.Builder().withText("ссылка").withUrl("url").build(),
                        "new Link.Builder()...")
                .build();
        System.out.println(demoTable);

    }
}
