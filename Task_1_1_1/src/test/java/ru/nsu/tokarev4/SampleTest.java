package ru.nsu.tokarev4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SampleTest {

    @Test
    void checkSorted() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, HeapSort.sort(input));
    }

    @Test
    void checkReverse() {
        int[] input = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, HeapSort.sort(input));
    }

    @Test
    void checkUnsorted() {
        int[] input = {453, 123, 21, 4, 1243, 23, 53};
        int[] expected = {4, 21, 23, 53, 123, 453, 1243};
        assertArrayEquals(expected, HeapSort.sort(input));
    }

    @Test
    void testMain() {
        HeapSort.main(new String[]{});
    }

    @Test
    void testheapify() {
        int[] input = {4, 3, 5, 2, 1};
        HeapSort.heapify(input, 5, 0);
        assertEquals(5, input[0]);
    }

    @Test
    void testHeapifyNoSwapNeeded() {
        int[] input = {5, 3, 4, 1, 2};
        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, HeapSort.sort(input));
    }

    @Test
    void checkSingleElement() {
        int[] input = {1};
        int[] expected = {1};
        assertArrayEquals(expected, HeapSort.sort(input));
    }

    @Test
    void checkEmptyArray() {
        int[] input = {};
        int[] expected = {};
        assertArrayEquals(expected, HeapSort.sort(input));
    }

    @Test
    void checkDuplicates() {
        int[] input = {5, 2, 5, 1, 2};
        int[] expected = {1, 2, 2, 5, 5};
        assertArrayEquals(expected, HeapSort.sort(input));
    }

    @Test
    void checkNeg() {
        int[] input = {-3, -1, -2, 0, 2, 1};
        int[] expected = {-3, -2, -1, 0, 1, 2};
        assertArrayEquals(expected, HeapSort.sort(input));
    }
}