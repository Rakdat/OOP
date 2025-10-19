package ru.nsu.tokarev4;

/**
 * Класс, представляющий игральную карту.
 * Содержит информацию о названии, масти и стоимости карты.
 */
class Card {
    /** Название карты (например, "Туз", "Король", "5") */
    String name;
    /** Масть карты (например, "Черви", "Пики") */
    String suit;
    /** Стоимость карты в очках */
    int value;
    /**
     * Создает новую карту с указанными параметрами.
     *
     * @param name название карты
     * @param suit масть карты
     * @param value стоимость карты в очках
     */
    public Card(String name, String suit, int value) {
        this.name = name;
        this.suit = suit;
        this.value = value;
    }
    /**
     * Возвращает строковое представление карты в формате: "Имя Масть (Стоимость)".
     */
    @Override
    public String toString() {
        return ": " + name + " " + suit + " (" + value+")";
    }
}