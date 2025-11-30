package ru.nsu.tokarev4;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Параметризованная хеш-таблица с разрешением коллизий методом цепочек.
 * Поддерживает основные операции за константное время в среднем случае.
 * @param <K> тип ключей в хеш-таблице
 * @param <V> тип значений в хеш-таблице
 */
public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {

    /**
     * Интерфейс для представления пары ключ-значение в хеш-таблице.
     * @param <K> тип ключа
     * @param <V> тип значения
     */
    public interface Entry<K, V> {
        /**
         * Возвращает ключ элемента.
         * @return ключ элемента
         */
        K getKey();

        /**
         * Возвращает значение элемента.
         * @return значение элемента
         */
        V getValue();
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Node<K, V>[] table;
    private int size;
    private int capacity;
    private int modCount;

    /**
     * Создает новую хеш-таблицу с вместимостью по умолчанию.
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        this.capacity = DEFAULT_CAPACITY;
        this.table = (Node<K, V>[]) new Node[capacity];
        this.size = 0;
        this.modCount = 0;
    }

    /**
     * Создает новую хеш-таблицу с указанной начальной вместимостью.
     * @param initialCapacity начальная вместимость таблицы
     * @throws IllegalArgumentException если initialCapacity меньше или равно 0
     */
    @SuppressWarnings("unchecked")
    public HashTable(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive: " + initialCapacity);
        }
        this.capacity = initialCapacity;
        this.table = (Node<K, V>[]) new Node[capacity];
        this.size = 0;
        this.modCount = 0;
    }

    /**
     * Добавляет пару ключ-значение в хеш-таблицу.
     * Если ключ уже существует, обновляет соответствующее значение.
     * @param key ключ для добавления
     * @param value значение для добавления
     */
    public void put(K key, V value) {
        if(size >= capacity * LOAD_FACTOR) {
            resize();
        }
        int hash = hash(key);
        int index = hash % capacity;
        // Проверяем существование ключа
        Node<K, V> current = table[index];
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                current.value = value;
                modCount++;
                return;
            }
            current = current.next;
        }
        // Добавляем новый узел
        Node<K, V> newNode = new Node<>(key, value, hash, table[index]);
        table[index] = newNode;
        size++;
        modCount++;
    }

    /**
     * Удаляет элемент с указанным ключом из хеш-таблицы.
     * @param key ключ элемента для удаления
     * @return значение удаленного элемента или null если ключ не найден
     */
    public V remove(K key) {
        int hash = hash(key);
        int index = hash % capacity;

        Node<K, V> current = table[index];
        Node<K, V> prev = null;
        while (current != null){
            if(Objects.equals(current.key, key)) {
                V removedValue = current.value;
                if(prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                modCount++;
                return removedValue;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    /**
     * Возвращает значение, связанное с указанным ключом.
     * @param key ключ для поиска
     * @return значение связанное с ключом или null если ключ не найден
     */
    public V get(K key) {
        int hash = hash(key);
        int index = hash % capacity;
        Node<K, V> current = table[index];
        while(current != null){
            if (Objects.equals(current.key, key)){
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Обновляет значение для указанного ключа.
     * @param key ключ для обновления
     * @param value новое значение
     * @return true если ключ найден и значение обновлено, false если ключ не найден
     */
    public boolean update(K key, V value) {
        int hash = hash(key);
        int index = hash % capacity;
        Node<K, V> current = table[index];
        while (current != null){
            if(Objects.equals(current.key, key)){
                current.value = value;
                modCount++;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Проверяет наличие указанного ключа в хеш-таблице.
     * @param key ключ для проверки
     * @return true если ключ существует в таблице, false в противном случае
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Возвращает количество элементов в хеш-таблице.
     * @return количество элементов в таблице
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли хеш-таблица.
     * @return true если таблица не содержит элементов, false в противном случае
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Очищает хеш-таблицу, удаляя все элементы.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        table = (Node<K, V>[]) new Node[capacity];
        size = 0;
        modCount++;
    }

    /**
     * Вычисляет хеш-код для указанного ключа.
     */
    private int hash(K key) {
        return key == null ? 0 : Math.abs(key.hashCode());
    }

    /**
     * Увеличивает вместимость хеш-таблицы и перераспределяет элементы.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCapacity];

        for(Node<K, V> node : table) {
            Node<K, V> current = node;
            while(current != null){
                Node<K, V> next = current.next;
                int newIndex = current.hash % newCapacity;
                current.next = newTable[newIndex];
                newTable[newIndex] = current;
                current = next;
            }
        }
        table = newTable;
        capacity = newCapacity;
        modCount++;
    }

    /**
     * Возвращает итератор для последовательного перебора элементов хеш-таблицы.
     *
     * @return итератор по элементам таблицы
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableIterator();
    }

    /**
     * Внутренний класс итератора для хеш-таблицы.
     */
    private class HashTableIterator implements Iterator<Entry<K, V>> {
        private int currentIndex;
        private Node<K, V> currentNode;
        private Node<K, V> nextNode;
        private final int expectedModCount;

        HashTableIterator() {
            this.currentIndex = 0;
            this.currentNode = null;
            this.expectedModCount = modCount;
            this.nextNode = findNextNode();
        }

        private Node<K, V> findNextNode() {
            if (currentNode != null && currentNode.next != null){
                return currentNode.next;
            }
            while(currentIndex < capacity) {
                if (table[currentIndex] != null){
                    return table[currentIndex++];
                }
                currentIndex++;
            }
            return null;
        }

        @Override
        public boolean hasNext() {
            checkForComodification();
            return nextNode != null;
        }

        @Override
        public Entry<K, V> next() {
            checkForComodification();
            if(!hasNext()) {
                throw new NoSuchElementException("No more elements in hash table");
            }
            currentNode = nextNode;
            nextNode = findNextNode();
            return currentNode;
        }
        private void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("HashTable was modified during iteration");
            }
        }
    }

    /**
     * Сравнивает данную хеш-таблицу с другим объектом на равенство.
     * @param obj объект для сравнения
     * @return true если таблицы равны, false в противном случае
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HashTable)){
            return false;
        }
        HashTable<?, ?> other = (HashTable<?, ?>) obj;
        if (this.size != other.size()){
            return false;
        }
        for (Entry<K, V> entry : this) {
            K key = entry.getKey();
            V value = entry.getValue();
            boolean found = false;
            Iterator<?> otherIterator = other.iterator();
            while (otherIterator.hasNext()){
                Entry<?, ?> otherEntry = (Entry<?, ?>) otherIterator.next();
                if(Objects.equals(key, otherEntry.getKey())) {
                    if (!Objects.equals(value, otherEntry.getValue())) {
                        return false;
                    }
                    found = true;
                    break;
                }
            }
            if (!found){
                return false;
            }
        }
        return true;
    }

    /**
     * Возвращает хеш-код хеш-таблицы.
     */
    @Override
    public int hashCode() {
        int result = 0;
        for (Entry<K, V> entry : this){
            result += Objects.hash(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * Возвращает строковое представление хеш-таблицы.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for(Entry<K, V> entry : this) {
            if(!first) {
                sb.append(", ");
            }
            sb.append(entry.toString());
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Внутренний класс для узлов цепочки разрешения коллизий.
     */
    private static class Node<K, V> implements Entry<K, V> {
        final K key;
        V value;
        final int hash;
        Node<K, V> next;

        Node(K key, V value, int hash, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}