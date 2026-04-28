package ru.nsu.tokarev4;

import java.util.List;

import static ru.nsu.tokarev4.PizUtil.getGameTime;

/**
 * Класс, описывающий работу курьера.
 * Курьер забирает пиццы со склада (до заполнения багажника),
 * доставляет их клиентам и возвращается обратно.
 */
public class Courier implements Runnable {
    private final int trunkCapacity;
    private final CustomQueue<Order> storage;
    private final long startTime;

    /**
     * Конструктор курьера.
     * @param trunkCapacity вместимость багажника (максимальное количество пицц за рейс).
     * @param startTime     время начала рабочего дня в миллисекундах.
     * @param storage       склад с готовыми пиццами.
     */
    public Courier(int trunkCapacity, long startTime, CustomQueue<Order> storage) {
        this.trunkCapacity = trunkCapacity;
        this.storage = storage;
        this.startTime = startTime;
    }

    @Override
    public void run() {
        try {
            while (true) {
                List<Order> trunk = storage.takeMany(trunkCapacity);
                if (trunk.isEmpty()) break; // Склад закрыт и пуст

                // Доставка к клиентам
                int deliveryTime = 100 + (int) (Math.random() * 201);
                for (Order order : trunk) {
                    System.out.println("[" + order.getId() + "] В доставке (Время доставки: " +
                            getGameTime(deliveryTime) +")");
                }
                Thread.sleep(deliveryTime);

                for (Order order : trunk) {
                    System.out.println("[" + order.getId() + "] Доставлен в " + getGameTime(startTime, System.currentTimeMillis()));
                }

                // Возвращение в пиццерию
                Thread.sleep(deliveryTime);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}