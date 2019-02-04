/*
 * Фоат Шарафутдинов
 * Домашняя работа второго занятия
 */

package ru.inno.lec02HomeWork;

import ru.inno.lec02HomeWork.Sorters.ArrayHelper;
import ru.inno.lec02HomeWork.Sorters.BogoSort;
import ru.inno.lec02HomeWork.Sorters.MergeSort;

import java.util.Arrays;

/**
 * Класс демонстрирующий работу методов сортировки BogoSort и MergeSort
 *
 * @author FOAT
 * @version 1.0  19.01.2019
 */
public class Lec02HomeWork {

    /**
     * Размер сортируемого массива
     * При включённом методе BogoSort не рекомендуется устанавливать
     * это значение большим 10, т.к. метод крайне не эффективный и медленный
     */
    final static int ARR_SIZE = 10;

    public static void main(String[] args) throws Exception {
        Integer[] arr = new Integer[ARR_SIZE];
        Integer[] arr2;

        try {
            ArrayHelper.fillRandomArray(arr, -100, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        arr2 = Arrays.copyOf(arr, arr.length);

        System.out.println("исходный массив:");
        System.out.println(Arrays.toString(arr));

        BogoSort.sort(arr);
        MergeSort.sort(arr2);

        System.out.println();
        printResult("BogoSort", arr);
        printResult("MergeSort", arr2);
    }

    /**
     * метод выводит результат сортировки
     *
     * @param method название метода сортировки
     * @param arr    обработанный массив
     */
    private static void printResult(String method, Integer[] arr) {
        System.out.println(method + ":");
        if (!ArrayHelper.isSorted(arr)) {
            System.out.println("не работает!");
        } else {
            System.out.println(Arrays.toString(arr));
        }
        System.out.println();
    }
}

