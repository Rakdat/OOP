package ru.nsu.tokarev4;

/**
 * Класс, представляющий заказ в пиццерии.
 * Содержит только идентификатор заказа.
 */
public class Order {
    private final int id;

    /**
     * Конструктор для создания нового заказа.
     * @param id уникальный идентификатор заказа.
     */
    public Order(int id) {
        this.id = id;
    }

    /**
     * Возвращает идентификатор заказа.
     * @return идентификатор заказа.
     */
    public int getId() {
        return id;
    }
}