package ru.nsu.tokarev4;

/**
 * Класс, представляющий задачу (чекбокс) в формате Markdown.
 * Используется для создания списков задач (TODO-листов).
 */
public class Task extends Element {
    private final String text;
    private final boolean checked;

    /**
     * Создает задачу с указанным текстом и статусом выполнения.
     * @param text текст задачи
     * @param checked true - задача выполнена, false - задача не выполнена
     */
    Task(String text, boolean checked) {
        this.text = text;
        this.checked = checked;
    }

    @Override
    public String serialize() {
        return "- [" + (checked ? "x" : " ") + "] " + text;
    }

    /**
     * Встроенный класс Builder для создания объектов Task.
     * Реализует паттерн Builder для удобного конструирования задач.
     */
    public static class Builder {
        private String text;
        private boolean checked = false;

        /**
         * Устанавливает текст задачи.
         */
        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        /**
         * Устанавливает статус выполнения задачи.
         */
        public Builder withChecked(boolean checked) {
            this.checked = checked;
            return this;
        }

        /**
         * Создает объект Task на основе заданных параметров.
         * Проверяет, что текст задачи не пустой.
         * @return новый объект Task
         * @throws IllegalStateException если текст равен null или пустой строке
         */
        public Task build() {
            if (text == null || text.isEmpty()) {
                throw new IllegalStateException("Task text cannot be empty");
            }
            return new Task(text, checked);
        }
    }
}