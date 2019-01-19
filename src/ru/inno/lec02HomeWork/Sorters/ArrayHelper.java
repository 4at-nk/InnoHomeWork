package ru.inno.lec02HomeWork.Sorters;

import ru.inno.RangedRandom;

/**
 * Класс содержащий вспомогательные функции для работы с массивом
 *
 * @author FOAT
 * @version 1.0  19.01.2019
 */
public class ArrayHelper {

    /**
     * метод заполняет массив рандомными значениями
     *
     * @param arr ссылка на массив, который нужно заполнить
     * @param min минимальная граница рандомного значения (включительная)
     * @param max максимальная граница рандомного значения (включительная)
     * @throws Exception {@link ru.inno.RangedRandom#getRandInt(int, int)}
     */
    public static void fillRandomArray(Integer[] arr, int min, int max)
            throws Exception {
        if (arr == null) {
            throw new NullPointerException("Массив неинициализирован");
        }

        for (int i = 0; i < arr.length; ++i) {
            arr[i] = RangedRandom.getRandInt(min, max);
        }
    }

    /**
     * метод проверяет, отсортирован ли массив
     *
     * @param arr ссылка на массив, который нужно проверить
     * @return true - отсортирован, false - не отсортирован
     */
    public static boolean isSorted(Integer[] arr) {
        if (arr == null) {
            throw new NullPointerException("Массив неинициализирован");
        }

        for (int i = 0; i < arr.length - 1; ++i) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }

        return true;
    }
}