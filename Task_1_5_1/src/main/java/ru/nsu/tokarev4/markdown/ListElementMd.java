package ru.nsu.tokarev4.markdown;

import ru.nsu.tokarev4.model.AbstractElement;
import ru.nsu.tokarev4.model.Element;
import ru.nsu.tokarev4.model.ListElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий список (упорядоченный или неупорядоченный) в формате Markdown.
 * Поддерживает вложенность элементов списка.
 */
public class ListElementMd extends AbstractElement implements ListElement {
    private final List<ListItemMd> items = new ArrayList<>();
    private final boolean ordered;

    /**
     * Создает список с указанной упорядоченностью.
     * @param ordered true - упорядоченный список, false - неупорядоченный список
     */
    private ListElementMd(boolean ordered) {
        this.ordered = ordered;
    }

    /**
     * Добавляет элемент в список с указанным уровнем вложенности.
     * Используется внутренне Builder'ом.
     * @param content содержимое элемента списка
     * @param level уровень вложенности (0 - корневой уровень)
     * @return текущий экземпляр ListElement для цепочки вызовов
     */
    ListElementMd addItem(Element content, int level) {
        items.add(new ListItemMd(content, level));
        return this;
    }

    /**
     * Сериализует список в строку Markdown.
     * Для упорядоченных списков заменяет маркеры "-" на нумерацию "1.", "2.", и т.д.
     * @return строковое представление списка в формате Markdown
     */
    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            ListItemMd item = items.get(i);
            String serialized = item.serialize();
            if (ordered) {
                serialized = serialized.replaceFirst("- ", (i + 1) + ". ");
            }
            sb.append(serialized).append("\n");
        }
        return sb.toString();
    }

    /**
     * Встроенный класс Builder для создания объектов ListElement.
     * Реализует паттерн Builder для удобного конструирования списков.
     */
    public static class Builder {
        private boolean ordered = false;
        private final List<Object[]> items = new ArrayList<>();

        /**
         * Устанавливает тип списка.
         */
        public Builder withOrdered(boolean ordered) {
            this.ordered = ordered;
            return this;
        }

        /**
         * Добавляет элемент списка с указанным уровнем вложенности.
         */
        public Builder addItem(Element content, int level) {
            items.add(new Object[]{content, level});
            return this;
        }

        /**
         * Добавляет элемент списка на корневом уровне (уровень 0).
         * @param content содержимое элемента списка
         * @return текущий экземпляр Builder для цепочки вызовов
         */
        public Builder addItem(Element content) {
            return addItem(content, 0);
        }

        /**
         * Создает объект ListElement на основе заданных параметров.
         * @return новый объект ListElement
         */
        public ListElementMd build() {
            ListElementMd list = new ListElementMd(ordered);
            for (Object[] item : items) {
                list.addItem((Element) item[0], (int) item[1]);
            }
            return list;
        }
    }
}