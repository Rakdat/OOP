package ru.nsu.tokarev4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Набор тестов для проверки логики игры "Змейка" (SnakeModel).
 */
class SnakeMainTest {

    private SnakeModel model;

    // Этот метод запускается автоматически ПЕРЕД каждым тестом, чтобы создать чистую модель
    @BeforeEach
    void setUp() {
        // Создаем поле 20х20, 5 еды
        model = new SnakeModel(20, 20, 5);
    }

    @Test
    void testInitialState() {
        // Проверяем, что при старте змея состоит из 1 квадратика (только голова)
        assertEquals(1, model.getSnakeBody().size(), "Змея должна рождаться размером 1");

        // Проверяем, что счет равен 0
        assertEquals(0, model.getScore(), "Начальный счет должен быть 0");

        // Проверяем, что еда сгенерировалась
        assertEquals(5, model.getFoods().size(), "Должно быть ровно 5 кусочков еды");

        // Игра не должна быть проиграна на старте
        assertFalse(model.isGameOver(), "Игра не должна заканчиваться при старте");
    }

    @Test
    void testSnakeMovement() {
        // Запоминаем изначальную позицию головы
        SnakeModel.Point initialHead = model.getSnakeBody().getFirst();
        int startX = initialHead.x;
        int startY = initialHead.y;

        // По умолчанию змея смотрит ВПРАВО. Делаем шаг.
        model.update();

        SnakeModel.Point newHead = model.getSnakeBody().getFirst();

        // Проверяем, что змея сдвинулась на 1 клетку вправо (x увеличился, y остался тем же)
        assertEquals(startX + 1, newHead.x, "Змея должна была сдвинуться вправо по оси X");
        assertEquals(startY, newHead.y, "Позиция Y не должна была измениться");
    }

    @Test
    void testDirectionChangeRestriction() {
        // Пытаемся заставить змею (которая идет ВПРАВО) резко пойти ВЛЕВО (на 180 градусов)
        model.setDirection(SnakeModel.Direction.LEFT);

        // Направление не должно было измениться, сработает защита от самоубийства
        assertEquals(SnakeModel.Direction.RIGHT, model.getCurrentDirection(), "Змее нельзя разворачиваться на 180 градусов");

        // А вот вверх повернуть можно!
        model.setDirection(SnakeModel.Direction.UP);
        assertEquals(SnakeModel.Direction.UP, model.getCurrentDirection(), "Змея должна уметь поворачивать на 90 градусов");
    }

    @Test
    void testWallCollision() {
        // Создаем крошечное поле 3х3
        SnakeModel smallModel = new SnakeModel(3, 3, 1);
        // Голова появится в центре (1, 1). Делаем два шага вправо, чтобы врезаться в стену.
        smallModel.update(); // Шаг на (2, 1) - это край поля
        smallModel.update(); // Шаг на (3, 1) - это ЗА краем поля

        // Проверяем, что наступил конец игры
        assertTrue(smallModel.isGameOver(), "Змея должна умереть при врезании в стену");
    }
}