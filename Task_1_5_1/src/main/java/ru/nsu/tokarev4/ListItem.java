package ru.nsu.tokarev4;

/**
 * Внутренний класс, представляющий элемент списка.
 * Не предназначен для непосредственного использования клиентами библиотеки.
 * Используется классом ListElement для хранения элементов списка.
 * Элемент списка включает содержимое и уровень вложенности,
 * который определяет количество отступов перед элементом.
 */
class ListItem extends Element {
    private final Element content;
    private final int level;

    /**
     * Создает элемент списка с указанным содержимым и уровнем вложенности.
     * Уровень автоматически ограничивается снизу значением 0.
     * @param content содержимое элемента списка
     * @param level уровень вложенности (не может быть меньше 0)
     */
    ListItem(Element content, int level) {
        this.content = content;
        this.level = Math.max(0, level);
    }

    /**
     * Сериализует элемент списка в строку Markdown.
     * Формат: отступы (по 2 пробела на каждый уровень вложенности) + "- " + содержимое.
     * @return строковое представление элемента списка в формате Markdown
     */
    @Override
    public String serialize() {
        String indent = "  ".repeat(level);
        return indent + "- " + content.serialize();
    }
}