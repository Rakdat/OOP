package ru.nsu.tokarev4;

/**
 * Класс, описывающий работу пекаря.
 * Пекарь берет заказ из очереди, готовит его случайное время
 * и помещает готовую пиццу на склад.
 */
public class Baker implements Runnable {
    private final CustomQueue<Order> orderQueue;
    private final CustomQueue<Order> storage;
    private final long startTime;
    private final int oneminuteinmc = 1000/60;

    /**
     * Конструктор пекаря.
     * @param startTime  время начала рабочего дня в миллисекундах.
     * @param orderQueue очередь входящих заказов.
     * @param storage    склад для готовых пицц.
     */
    public Baker(long startTime, CustomQueue<Order> orderQueue, CustomQueue<Order> storage) {
        this.orderQueue = orderQueue;
        this.storage = storage;
        this.startTime = startTime;
    }

    /**
     * Форматирует время готовки в игровые "минуты".
     * @param cookingtime реальное время готовки в миллисекундах.
     * @return отформатированная строка с минутами.
     */
    private String getRightTIme(int cookingtime) {
        return (cookingtime / oneminuteinmc + " мин");
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 1. Пекарь берет заказ из общей очереди
                Order order = orderQueue.take();
                if (order == null) break; // Смена окончена, заказов больше нет

                // 2. Генерируем уникальное время готовки для конкретно этого заказа от 200 до 800 мс
                int cookingTime = 200 + (int) (Math.random() * 601);

                System.out.println("[" + order.getId() + "] Готовится (Время готовки: " + getRightTIme(cookingTime) + ")");

                // 3. Имитируем процесс готовки
                Thread.sleep(cookingTime);

                System.out.println("[" + order.getId() + "] Ожидает место на складе");

                // 4. Кладем на склад (если склад полон, пекарь сам уснет внутри метода put, пока курьер не освободит место)
                storage.put(order);

                System.out.println("[" + order.getId() + "] На складе");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}