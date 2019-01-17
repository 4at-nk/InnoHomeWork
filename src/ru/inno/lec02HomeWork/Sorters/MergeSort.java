package ru.inno.lec02HomeWork.Sorters;

/**
 * Класс реализующий сортировку массива Integer методом MergeSort
 *
 * @author FOAT
 * @version 1.0  16.01.2019
 */
public class MergeSort {

    /**
     * Метод совершающий слияние двух массивов в один
     *
     * @param arr  ссылка на результирующий массив
     * @param arr1 ссылка на первый массив
     * @param arr2 ссылка на второй массив
     */
    private static void merge(Integer[] arr, Integer[] arr1, Integer[] arr2) {
        int i1 = 0;
        int i2 = 0;

        for (int i = 0; i < arr.length; ++i) {
            if (i1 == arr1.length) {
                arr[i] = arr2[i2++];
            } else if (i2 == arr2.length) {
                arr[i] = arr1[i1++];
            } else if (arr1[i1] < arr2[i2]) {
                arr[i] = arr1[i1++];
            } else {
                arr[i] = arr2[i2++];
            }
        }
    }

    /**
     * Метод выполняет сортировку
     *
     * @param arr ссылка на массив, который нужно отсортировать
     */
    public static void sort(Integer[] arr) {
        if (arr == null) {
            return;
        }

        if (arr.length <= 1) {
            return;
        }

        //определяем, является ли количество элементов в массиве чётным
        final int plusOne = arr.length % 2 == 0 ? 0 : 1;
        Integer[] arr1 = new Integer[arr.length / 2];
        //если количество нечётное, то оставшийся элемент будем класть во вотрой подмассив
        Integer[] arr2 = new Integer[arr.length / 2 + plusOne];

        System.arraycopy(arr, 0, arr1, 0, arr.length / 2);
        System.arraycopy(arr, arr.length / 2, arr2, 0,
                arr.length / 2 + plusOne);
        sort(arr1);
        sort(arr2);
        merge(arr, arr1, arr2);
    }
}
