/*
 * Фоат Шарафутдинов
 * Домашняя работа пятого занятия
 *
 * Класс запускает для каждого файла свой поток обработки, который в свою очередь
 * запускает для каждого искомого слова свой поток поиска в блоке текста из файла.
 *
 * Производительность:
 * Количество потоков для файла оценивались на маленьких и средних файлах в большом количестве,
 * т. к. для них имеет смысл паралельное выполнение нескольких файлов, а распараллеливание их обработки
 * не заметно:
 *
 * 1 поток 100 слов
 * 2000 files 1.38 kB time: 28.389 sec.
 * 100 files 52.6 kB time: 9.655 sec.

 * 2 потока 100 слов
 * 2000 files 1.38 kB time: 19.385 sec.
 * 100 files 52.6 kB time: 5.847 sec.

 * 3 потока 100 слов
 * 2000 files 1.38 kB time: 16.801 sec.
 * 100 files 52.6 kB time: 4.769 sec.

 * 4 потока 100 слов
 * 2000 files 1.38 kB time: 16.831 sec.
 * 100 files 52.6 kB time: 4.467 sec.
 *
 * Далее производительность не растёт...
 *
 *
 * Количество потоков обработки файла оценивались на большом файле, т. к. здесь
 * заметна разность в скоростях.
 *
 * 1 поток 3 слова
 * 1 file 1 gB time: 79.11 sec.
 *
 * 2 потока 3 слова
 * 1 file 1 gB time: 63.902 sec.
 *
 * 3 потока 3 слова
 * 1 file 1 gB time: 55.702 sec.
 */

package ru.inno.lec05HomeWork.Occurences;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Класс для поиска предложений из файлов, в которых встречаются искомые слова
 * и записи их в файл
 *
 * @author FOAT
 * @version 1.0  22.01.2019
 */
public class OccurencesFinder {
    /*
     * запускает для каждого файла свой поток обработки
     */

    /**
     * максимальное количество потоков FileReadThread
     */
    private static final int MAX_THREAD_COUNT = 3;

    /**
     * Находит предложения, в которых встречаются искомые слова и
     * записывает их в файл
     *
     * @param sources список файлов, которые нужно проверить
     * @param words   список искомых слов
     * @param res     полное имя файла, куда нужно записать результат
     * @throws IOException при проблемах с открытием файлов и записью в файлы
     */
    public static void getOccurences(String[] sources, String[] words, String res) throws IOException, InterruptedException {
        //открываем файл для записи результата
        try (SentencesWriter sentencesWriter = new SentencesWriter(res)) {
            ArrayList<Thread> threads = new ArrayList<>();

            //для каждого файла запускаем поток поиска слов
            //(если файлов не больше, чем максимальное разрешенное количество потоков)
            //в файлах FileReadThread
            int curPos = 0;
            while (curPos < sources.length) {
                int countToLaunch = Integer.min(MAX_THREAD_COUNT, sources.length - curPos);

                for (int i = 0; i < countToLaunch; ++i) {
                    threads.add(new FileReadThread(sources[curPos++], words, sentencesWriter));
                    threads.get(i).start();
                }

                for (Thread thread : threads) {
                    thread.join();
                }

                threads.clear();
            }
        }
    }
}
