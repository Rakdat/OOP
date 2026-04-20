package ru.nsu.tokarev4;

import java.util.List;

/**
 * Класс, описывающий работу курьера.
 * Курьер забирает пиццы со склада (до заполнения багажника),
 * доставляет их клиентам и возвращается обратно.
 */
public class Courier implements Runnable {
    private final int trunkCapacity;
    private final CustomQueue<Order> storage;
    private final long startTime;
    private final int oneminuteinmc = 1000/60;

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

    /**
     * Рассчитывает игровое время на основе прошедших миллисекунд от начала смены.
     *
     * @param deltime время доставки (если 0, выводит текущее игровое время).
     * @return отформатированная строка со временем.
     */
    private String getRightTIme(int deltime) {
        long timenow = System.currentTimeMillis();
        if (deltime != 0){
            return (deltime / oneminuteinmc + " мин");
        }
        int hours = 8 + (int) (timenow - startTime) / 1000;
        int minutes = (int) (timenow - startTime) % 1000 / oneminuteinmc;
        if (minutes < 10) {
            return (hours % 24 + ":0" + minutes);
        }
        if(minutes == 60){
            hours++;
            return (hours % 24 + ":00");
        }
        return (hours % 24 + ":" + minutes);
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
                    System.out.println("[" + order.getId() + "] В доставке (Время доставки: " + getRightTIme(deliveryTime) +")");
                }
                Thread.sleep(deliveryTime);

                for (Order order : trunk) {
                    long timenow = System.currentTimeMillis();
                    System.out.println("[" + order.getId() + "] Доставлен в " + getRightTIme(0));
                }

                // Возвращение в пиццерию
                Thread.sleep(deliveryTime);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}