package ru.nsu.tokarev4;

class Card {
    String name; // имя карты
    String suit; // ее масть
    int value; // ее значение
    public Card(String name, String suit, int value) {
        this.name = name;
        this.suit = suit;
        this.value = value;
    }
    @Override
    public String toString() { // для корректного вывода значений карты
        return ": " + name + " " + suit + " (" + value+")";
    }
}