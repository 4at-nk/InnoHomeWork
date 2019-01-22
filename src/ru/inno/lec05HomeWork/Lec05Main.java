/*
 * Фоат Шарафутдинов
 * Домашняя работа пятого занятия
 *
 * Выводы по производительности в OccurencesFinder.java
 */

package ru.inno.lec05HomeWork;

import ru.inno.lec05HomeWork.Occurences.OccurencesFinder;

/**
 * Класс демонстрирующий работу метода getOccurences() класса OccurencesFinder
 *
 * @author FOAT
 * @version 1.0  22.01.2019
 */
public class Lec05Main {

    public static void main(String[] args) throws Exception {
        String[] words = {"есть", "шесть", "жизнь"};
        String[] files = {".\\tmp\\hw5\\example"};

        OccurencesFinder.getOccurences(files, words, ".\\tmp\\hw5\\res\\res");
    }
}
