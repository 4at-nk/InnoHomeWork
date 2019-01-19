/*
 * Фоат Шарафутдинов
 * Домашняя работа четвёртого занятия
 */

package ru.inno.lec04HomeWork;

import ru.inno.lec04HomeWork.Builders.FileBuilder;

/**
 * Класс демонстрирующий работу метода getFiles() класса FileBuilder
 *
 * @author FOAT
 * @version 1.0  19.01.2019
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String[] words = {"mir", "trud", "may"};
        FileBuilder.getFiles(".\\", 3, 3, words, 5);
    }
}
