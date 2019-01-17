package ru.inno.lec02HomeWork.Sorters;

import java.util.Random;

/**
 * Класс содержащий вспомогательные функции
 *
 * @author FOAT
 * @version 1.0  16.01.2019
 */
public class Helper {

    private static Random rnd = new Random();

    /**
     * метод заполняет массив рандомными значениями
     *
     * @param arr ссылка на массив, который нужно заполнить
     * @param min минимальная граница рандомного значения (включительная)
     * @param max максимальная граница рандомного значения (включительная)
     * @throws Exception @see getRandInt()
     */
    public static void fillRandomArray(Integer[] arr, int min, int max)
            throws Exception {
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = getRandInt(min, max);
        }
    }

    /**
     * метод проверяет, отсортирован ли массив
     *
     * @param arr ссылка на массив, который нужно проверить
     * @return true - отсортирован, false - не отсортирован
     */
    public static boolean isSorted(Integer[] arr) {
        for (int i = 0; i < arr.length - 1; ++i) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }

        return true;
    }

    /**
     * метод возвращает рандомное целое число
     *
     * @param min минимальная граница рандомного значения (включительная)
     * @param max максимальная граница рандомного значения (включительная)
     * @return рандомное целое число в пределах [min; max]
     * @throws Exception генерируется при условии min больше или равно max
     */
    public static int getRandInt(int min, int max) throws Exception {
        if (min >= max) {
            throw new Exception("Некорректно заданы границы диапазона "
                    + "случайного числа");
        }

        return rnd.nextInt(max - min + 1) + min;
    }
}