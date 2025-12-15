package ru.nsu.tokarev4.markdown;

import ru.nsu.tokarev4.model.AbstractElement;
import ru.nsu.tokarev4.model.Element;
import ru.nsu.tokarev4.model.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий таблицу в формате Markdown.
 * Поддерживает выравнивание столбцов, ограничение количества строк
 * и различные типы содержимого ячеек.
 * Использует паттерн Builder для пошагового создания таблицы.
 */
public class TableMd extends AbstractElement implements Table {
    /** Константы для выравнивания по краю */
    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_RIGHT = 2;

    private List<Integer> alignments;
    private final List<List<Element>> rows = new ArrayList<>();
    private int rowLimit = -1;

    private TableMd() {}

    /**
     * Вычисляет максимальную ширину для каждого столбца таблицы.
     * <p>Проходит по всем ячейкам каждого столбца и определяет максимальную длину
     * строкового представления. Это необходимо для красивого форматирования таблицы.</p>
     * @return массив целых чисел, где каждый элемент представляет ширину соответствующего столбца
     */
    private int[] calculateColumnWidths() {
        if (rows.isEmpty()) return new int[0];

        int colCount = rows.get(0).size();
        int[] widths = new int[colCount];

        for (List<Element> row : rows) {
            for (int i = 0; i < row.size(); i++) {
                String content = row.get(i).serialize();
                widths[i] = Math.max(widths[i], content.length());
            }
        }

        return widths;
    }

    /**
     * Добавляет пробелы к содержимому для выравнивания в ячейке заданной ширины.
     * ALIGN_LEFT: пробелы добавляются справа
     * ALIGN_CENTER: пробелы равномерно распределяются слева и справа
     * ALIGN_RIGHT: пробелы добавляются слева
     * @param content исходное содержимое ячейки
     * @param width общая ширина ячейки (включая пробелы)
     * @param alignment тип выравнивания (ALIGN_LEFT, ALIGN_CENTER или ALIGN_RIGHT)
     * @return выровненное содержимое с добавленными пробелами
     */
    private String padContent(String content, int width, int alignment) {
        int padding = width - content.length();

        switch (alignment) {
            case ALIGN_CENTER:
                int leftPadding = padding / 2;
                int rightPadding = padding - leftPadding;
                return " ".repeat(leftPadding) + content + " ".repeat(rightPadding);

            case ALIGN_RIGHT:
                return " ".repeat(padding) + content;

            case ALIGN_LEFT:
            default:
                return content + " ".repeat(padding);
        }
    }

    /**
     * Создает разделительную строку (separator) для указанного типа выравнивания.
     * Разделительная строка в Markdown таблицах определяет выравнивание столбца:
     * ALIGN_LEFT: :--- (двоеточие слева)
     * ALIGN_CENTER: :---: (двоеточия с обеих сторон)
     * ALIGN_RIGHT: ---: (двоеточие справа)
     * @param width ширина разделительной строки
     * @param alignment тип выравнивания столбца
     * @return разделительная строка в формате Markdown
     */
    private String getAlignmentMarker(int width, int alignment) {
        String dashes = "-".repeat(Math.max(3, width)); // Минимум 3 дефиса

        switch (alignment) {
            case ALIGN_CENTER:
                return ":" + dashes.substring(1, dashes.length() - 1) + ":";
            case ALIGN_RIGHT:
                return dashes.substring(0, dashes.length() - 1) + ":";
            case ALIGN_LEFT:
            default:
                return ":" + dashes.substring(1);
        }
    }


    /**
     * Сериализует таблицу в строку формата Markdown.
     * Процесс сериализации включает:
     * Вычисление ширины каждого столбца
     * Форматирование строки заголовка (первая строка всегда считается заголовком)
     * Создание разделительной строки с указанием выравнивания
     * Форматирование строк данных с учетом ограничения по количеству строк
     * @return таблица в формате Markdown
     */
    @Override
    public String serialize() {
        if (rows.isEmpty()) {
            return "";
        }

        int[] colWidths = calculateColumnWidths();
        StringBuilder sb = new StringBuilder();

        // Добавляем заголовок
        sb.append("|");
        for (int i = 0; i < rows.get(0).size(); i++) {
            Element cell = rows.get(0).get(i);
            String padded = padContent(cell.serialize(), colWidths[i], ALIGN_CENTER);
            sb.append(" ").append(padded).append(" |");
        }
        sb.append("\n");

        // Добавляем разделитель
        sb.append("|");
        for (int i = 0; i < rows.get(0).size(); i++) {
            int alignment = (alignments != null && i < alignments.size())
                    ? alignments.get(i) : ALIGN_LEFT;
            String marker = getAlignmentMarker(colWidths[i], alignment);
            sb.append(" ").append(marker).append(" |");
        }
        sb.append("\n");

        // Добавляем строки с данными
        int limit = (rowLimit > 0) ? Math.min(rowLimit, rows.size()) : rows.size();
        for (int i = 1; i < limit; i++) {
            sb.append("|");
            List<Element> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {
                Element cell = row.get(j);
                int alignment = (alignments != null && j < alignments.size())
                        ? alignments.get(j) : ALIGN_LEFT;
                String padded = padContent(cell.serialize(), colWidths[j], alignment);
                sb.append(" ").append(padded).append(" |");
            }
            if (i < limit - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Внутренний класс Builder для пошагового создания таблиц.
     *
     * <p>Реализует паттерн Builder, предоставляя fluent interface
     * (текучий интерфейс) для конфигурирования таблицы:</p>
     *
     * <pre>
     * Table table = new Table.Builder()
     *     .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
     *     .withRowLimit(10)
     *     .addRow("Заголовок 1", "Заголовок 2")
     *     .addRow("Данные 1", "Данные 2")
     *     .build();
     * </pre>
     */
    public static class Builder {
        private final TableMd tableMd = new TableMd();
        private boolean headerAdded = false;

        /**
         * Устанавливает выравнивание для столбцов таблицы.
         * @param alignments массив констант выравнивания (ALIGN_LEFT, ALIGN_CENTER, ALIGN_RIGHT)
         * @return текущий экземпляр Builder для цепочки вызовов
         */
        public Builder withAlignments(int... alignments) {
            tableMd.alignments = new ArrayList<>();
            for (int alignment : alignments) {
                tableMd.alignments.add(alignment);
            }
            return this;
        }

        /**
         * Устанавливает ограничение на количество отображаемых строк.
         */
        public Builder withRowLimit(int rowLimit) {
            tableMd.rowLimit = rowLimit;
            return this;
        }

        /**
         * Добавляет строку в таблицу.
         */
        public Builder addRow(Object... cells) {
            List<Element> row = new ArrayList<>();
            for (Object cell : cells) {
                row.add(createElement(cell));
            }
            if (!headerAdded) {
                tableMd.rows.add(0, row);
                headerAdded = true;
            } else {
                tableMd.rows.add(row);
            }
            return this;
        }

        /**
         * Преобразует объект в элемент Element.
         * Вспомогательный метод, который автоматически определяет тип объекта
         * и создает соответствующий элемент Markdown.
         * @param cell объект, который нужно преобразовать в Element
         * @return элемент Markdown, готовый для добавления в таблицу
         */
        private Element createElement(Object cell) {
            if (cell instanceof Element) {
                return (Element) cell;
            } else if (cell instanceof Integer) {
                return new TextMd(String.valueOf(cell));
            } else if (cell instanceof String) {
                return new TextMd((String) cell);
            } else {
                return new TextMd(cell.toString());
            }
        }

        /**
         * Завершает процесс построения и возвращает готовую таблицу.
         * @return полностью сконфигурированный объект Table
         */
        public TableMd build() {
            return tableMd;
        }
    }
}