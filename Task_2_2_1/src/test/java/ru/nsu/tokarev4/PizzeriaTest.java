package ru.nsu.tokarev4;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс для тестирования базовой логики пиццерии.
 */
class PizzeriaTest {

    @Test
    void testOrderCreation() {
        Order order = new Order(10);
        assertEquals(10, order.getId(), "ID заказа должен совпадать с переданным в конструктор");
    }

    @Test
    void testCustomQueuePutAndTake() throws InterruptedException {
        CustomQueue<Order> queue = new CustomQueue<>(5);
        Order orderIn = new Order(1);

        queue.put(orderIn);
        Order orderOut = queue.take();

        assertNotNull(orderOut, "Очередь должна вернуть объект");
        assertEquals(1, orderOut.getId(), "Извлеченный заказ должен быть тем же самым");
    }

    @Test
    void testCustomQueueTakeMany() throws InterruptedException {
        CustomQueue<Order> queue = new CustomQueue<>(10);
        queue.put(new Order(1));
        queue.put(new Order(2));
        queue.put(new Order(3));

        List<Order> taken = queue.takeMany(2); // Курьер берет только 2
        assertEquals(2, taken.size(), "Должно быть извлечено ровно 2 заказа");
        assertEquals(1, taken.get(0).getId(), "Первый заказ должен иметь ID 1");
        assertEquals(2, taken.get(1).getId(), "Второй заказ должен иметь ID 2");

        List<Order> takenRest = queue.takeMany(2); // Забираем остаток
        assertEquals(1, takenRest.size(), "Должен остаться только 1 заказ");
        assertEquals(3, takenRest.get(0).getId());
    }

    @Test
    void testCustomQueueDeactivation() throws InterruptedException {
        CustomQueue<Order> queue = new CustomQueue<>(5);
        queue.deactivate();

        Order orderOut = queue.take(); // Так как очередь пуста и деактивирована, должен вернуться null
        assertNull(orderOut, "Деактивированная пустая очередь должна возвращать null при take()");

        List<Order> listOut = queue.takeMany(3);
        assertTrue(listOut.isEmpty(), "Деактивированная пустая очередь должна возвращать пустой список при takeMany()");
    }
}