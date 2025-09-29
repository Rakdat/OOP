package ru.nsu.tokarev4;

public class HeapSort {
    public static int[] sort(int[] arr){
        if (arr == null || arr.length <= 1){ //в случае пустого или одноэлементного массива возвращаем его обратно
            return arr;
        }
        int len = arr.length;
        for (int i = (len / 2) - 1; i >= 0; i--){ //строим max-heap кучу
            heapify(arr, len, i);
        }
        for (int i = len - 1; i > 0; i--){ // извлекаем элементы из кучи
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0); //перестраиваем кучу
        }
        return arr;
    }

    public static void heapify(int[] arr, int len, int i){ //алгоритм перестановки элементов
        int left = i * 2 + 1; //выбираем левый элемент
        int right = i * 2 + 2; //выбираем правый элемент
        int big = i; //определяем вершину как наибольший элемент
        if (left < len && arr[left] > arr[big]){ //сравнение вершины с левым сыном
            big = left;
        }
        if (right < len && arr[right] > arr[big]){ //сравнение вершины с правым сыном
            big = right;
        }
        if (i != big){ //если один из сыновей оказался больше то меняем местами
            int temp = arr[i];
            arr[i] = arr[big];
            arr[big] = temp;
            heapify(arr, len, big); // просеиваем вниз дальше
        }
    }

    public static void main(String[] args){ //функция main для запуска тестового массива
        int[] arr = {2, 1, 5, 4, 3};
        System.out.print("Start mas\n");
        for (int i : arr){ 
            System.out.print(i + " "); //вывод неотсортированного массива
        }
        System.out.print("\n");
        sort(arr); //сортировка
        System.out.print("End mas\n");
        for (int i : arr){
            System.out.print(i + " "); //вывод отсортированного массива
        }
        System.out.print("\n");
    }
}
