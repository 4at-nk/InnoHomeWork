/*
 * Фоат Шарафутдинов
 * Домашняя работа пятого занятия
 *
 * Выводы по производительности в OccurencesFinder.java
 */

package ru.inno.lec05HomeWork;

import ru.inno.lec05HomeWork.Occurences.OccurencesFinder;
import ru.inno.lec05HomeWork.Occurences.SentencesWriter.FileSentencesWriter;

/**
 * Класс демонстрирующий работу метода getOccurences() класса OccurencesFinder
 *
 * @author FOAT
 * @version 1.0  05.02.2019
 */
public class Lec05Main {

    public static void main(String[] args) throws Exception {
        String[] words = {"есть", "шесть", "жизнь"};
        String[] files = {".\\tmp\\hw5\\example"};

        try (OccurencesFinder finder = new OccurencesFinder(new FileSentencesWriter())) {
            finder.getOccurences(files, words, ".\\tmp\\hw5\\res\\res");
        }

        /*try (OccurencesFinderStreamed finderStreamed =
                new OccurencesFinderStreamed(new FileSentencesWriter())) {
            finderStreamed.getOccurences(files, words, ".\\tmp\\hw5\\res\\res");
        }*/
    }
}
