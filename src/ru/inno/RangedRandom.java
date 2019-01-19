package ru.inno;

import java.util.Random;

/**
 * Класс для генерации случайных чисел
 *
 * @author FOAT
 * @version 1.0  19.01.2019
 */
public class RangedRandom {

    /** генератор случайных чисел */
    private static Random rnd = new Random();

    /**
     * метод возвращает рандомное целое число
     *
     * @param min минимальная граница рандомного значения (включительная)
     * @param max максимальная граница рандомного значения (включительная)
     * @return рандомное целое число в пределах [min; max]
     * @throws Exception генерируется при условии min больше max
     */
    public static int getRandInt(int min, int max) throws Exception {
        if (min > max) {
            throw new Exception("Некорректно заданы границы диапазона "
                    + "случайного числа");
        } else if (min == max) {
            return min;
        }

        return rnd.nextInt(max - min + 1) + min;
    }

    /**
     * Определяет рандомно произошло ли событие с определённой вероятностью
     *
     * @param probability вероятность в процентах (от 0 до 100)
     * @return true - событие произошло, false - событие не произошло
     * @throws Exception генерируется при некорректной вероятности
     */
    public static boolean isItHappen(double probability) throws Exception {
        if (probability < 0.0 || probability > 100.0) {
            throw new Exception("Некорректная вероятность");
        }

        return probability != 0.0
                && RangedRandom.rnd.nextDouble() * 100.0 <= probability;
    }
}
