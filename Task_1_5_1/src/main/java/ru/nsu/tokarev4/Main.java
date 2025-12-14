package ru.nsu.tokarev4;

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

        Text plainText = new Text("Обычный текст");
        Text.Bold boldText = new Text.Bold("Жирный текст");
        Text.Italic italicText = new Text.Italic("Курсивный текст");
        Text.Strikethrough strikeText = new Text.Strikethrough("Зачеркнутый текст");
        Text.InlineCode codeText = new Text.InlineCode("код");

        System.out.println("Обычный: " + plainText);
        System.out.println("Жирный: " + boldText);
        System.out.println("Курсив: " + italicText);
        System.out.println("Зачеркнутый: " + strikeText);
        System.out.println("Встроенный код: " + codeText);

        // Комбинированное форматирование
        System.out.println("\nКомбинирование:");
        System.out.println(new Text("Текст с " +
                new Text.Bold("жирным") + " и " +
                new Text.Italic("курсивным") + " форматированием"));

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
                .addItem(new Text("Первый элемент"))
                .addItem(new Text("Второй элемент с " + new Text.Bold("жирным текстом")))
                .addItem(new Text("Вложенный элемент"), 1)
                .addItem(new Text("Еще более вложенный"), 2)
                .addItem(new Text("Третий элемент"))
                .build();
        System.out.println(unorderedList);

        System.out.println("Упорядоченный список:");
        ListElement orderedList = new ListElement.Builder()
                .withOrdered(true)
                .addItem(new Text("Шаг первый"))
                .addItem(new Text("Шаг второй"))
                .addItem(new Text("Шаг третий"))
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
                .addElement(new Text("Это важная цитата"))
                .addElement(new Text("из нескольких строк"))
                .addElement(new Text("с " + new Text.Italic("курсивным") + " текстом"))
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
                taskTableBuilder.addRow(i, new Text.Bold(String.valueOf(value)));
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
                        new Text.Bold("текст"),
                        "new Text.Bold(\"текст\")")
                .addRow("Курсив",
                        new Text.Italic("текст"),
                        "new Text.Italic(\"текст\")")
                .addRow("Зачеркнутый",
                        new Text.Strikethrough("текст"),
                        "new Text.Strikethrough(\"текст\")")
                .addRow("Код",
                        new Text.InlineCode("код"),
                        "new Text.InlineCode(\"код\")")
                .addRow("Ссылка",
                        new Link.Builder().withText("ссылка").withUrl("url").build(),
                        "new Link.Builder()...")
                .build();
        System.out.println(demoTable);

    }
}
