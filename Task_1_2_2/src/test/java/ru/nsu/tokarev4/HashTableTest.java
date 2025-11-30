package ru.nsu.tokarev4;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Юнит-тесты для класса HashTable
 */
public class HashTableTest {

    private HashTable<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new HashTable<>();
    }

    /**
     * Тест 1: Создание пустой хеш-таблицы
     */
    @Test
    void testCreateEmptyHashTable() {
        assertTrue(hashTable.isEmpty());
        assertEquals(0, hashTable.size());
    }

    /**
     * Тест 2: Добавление пары ключ-значение
     */
    @Test
    void testPut() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        assertFalse(hashTable.isEmpty());
        assertEquals(2, hashTable.size());
        assertEquals(1, hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
    }

    /**
     * Тест 3: Обновление значения по ключу
     */
    @Test
    void testUpdateExistingKey() {
        hashTable.put("key", 10);
        hashTable.put("key", 20); // Обновление

        assertEquals(1, hashTable.size());
        assertEquals(20, hashTable.get("key"));
    }

    /**
     * Тест 4: Удаление пары ключ-значение
     */
    @Test
    void testRemove() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        Integer removed = hashTable.remove("one");
        assertEquals(1, removed);
        assertEquals(1, hashTable.size());
        assertNull(hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
    }

    /**
     * Тест 5: Удаление несуществующего ключа
     */
    @Test
    void testRemoveNonExistentKey() {
        hashTable.put("one", 1);

        assertNull(hashTable.remove("nonexistent"));
        assertEquals(1, hashTable.size());
    }

    /**
     * Тест 6: Поиск значения по ключу
     */
    @Test
    void testGet() {
        hashTable.put("apple", 5);
        hashTable.put("banana", 10);

        assertEquals(5, hashTable.get("apple"));
        assertEquals(10, hashTable.get("banana"));
        assertNull(hashTable.get("orange"));
    }

    /**
     * Тест 7: Обновление значения через update метод
     */
    @Test
    void testUpdateMethod() {
        hashTable.put("key", 100);

        assertTrue(hashTable.update("key", 200));
        assertEquals(200, hashTable.get("key"));

        assertFalse(hashTable.update("nonexistent", 300));
    }

    /**
     * Тест 8: Проверка наличия ключа
     */
    @Test
    void testContainsKey() {
        hashTable.put("exists", 1);

        assertTrue(hashTable.containsKey("exists"));
        assertFalse(hashTable.containsKey("nonexistent"));
    }

    /**
     * Тест 9: Очистка таблицы
     */
    @Test
    void testClear() {
        hashTable.put("a", 1);
        hashTable.put("b", 2);
        hashTable.put("c", 3);

        hashTable.clear();

        assertTrue(hashTable.isEmpty());
        assertEquals(0, hashTable.size());
        assertNull(hashTable.get("a"));
    }

}