package ru.nsu.tokarev4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Потокобезопасная очередь для хранения заказов и готовых пицц.
 * Реализована на основе мониторов (wait/notifyAll).
 * @param <T> тип элементов, хранящихся в очереди (в нашем случае Order).
 */
public class CustomQueue<T> {
    private final LinkedList<T> queue = new LinkedList<>();
    private final int capacity;
    private boolean active = true;

    /**
     * Создает новую очередь с заданным ограничением вместимости.
     * @param capacity максимальное количество элементов в очереди.
     */
    public CustomQueue(int capacity) {
        this.capacity = capacity;
    }


    /**
     * Добавляет элемент в очередь. Если очередь заполнена, поток блокируется
     * до появления свободного места или до деактивации очереди.
     * @param item элемент для добавления.
     */
    public synchronized void put(T item) throws InterruptedException {
        while (queue.size() >= capacity && active) {
            wait(); // Ожидаем, если склад полон
        }
        if (!active && queue.size() >= capacity) return;
        queue.add(item);
        notifyAll(); // Будим потребителей
    }


    /**
     * Извлекает один элемент из очереди. Если очередь пуста, поток блокируется
     * до появления новых элементов или деактивации очереди.
     * @return извлеченный элемент или null, если очередь пуста и деактивирована.
     */
    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty() && active) {
            wait();
        }
        if (queue.isEmpty() && !active) return null;
        T item = queue.poll();
        notifyAll();
        return item;
    }


    /**
     * Извлекает несколько элементов из очереди за один раз.
     * Если очередь пуста, поток блокируется.
     * @param maxElements максимальное количество элементов для извлечения.
     * @return список извлеченных элементов.
     */
    public synchronized List<T> takeMany(int maxElements) throws InterruptedException {
        while (queue.isEmpty() && active) {
            wait();
        }
        List<T> list = new ArrayList<>();
        if (queue.isEmpty() && !active) return list;

        int count = 0;
        while (!queue.isEmpty() && count < maxElements) {
            list.add(queue.poll());
            count++;
        }
        notifyAll();
        return list;
    }

    /**
     * Деактивирует очередь. Завершает прием новых элементов при переполнении
     * и будит все ожидающие потоки, чтобы они могли корректно завершить работу.
     */
    public synchronized void deactivate() {
        active = false;
        notifyAll(); // Будим всех зависших в wait() для завершения потоков
    }
}