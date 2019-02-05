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
 *
 * Новый метод через Stream API не дал прироста при 1 файле в гигабайт,
 * но 2000 файлов по 1.38 кб делает за 3.432 sec. (против ~17 sec.)
 */

package ru.inno.lec05HomeWork.Occurences;

import ru.inno.lec05HomeWork.Occurences.SentencesWriter.SentencesWriter;

import java.io.IOException;

/**
 * Класс для поиска предложений из файлов, в которых встречаются искомые слова
 * и записи их в файл
 *
 * @author FOAT
 * @version 1.0  05.02.2019
 */
public class OccurencesFinder implements AutoCloseable {
    /*
     * запускает для каждого файла свой поток обработки
     */

    /**
     * максимальное количество потоков FileReadThread
     */
    private static final int MAX_THREAD_COUNT = 3;
    /**
     * объект для запуска новых потоков
     */
    private ThreadLauncher threadLauncher = new ThreadLauncher();
    /**
     * объект для записи результата
     */
    private SentencesWriter sentencesWriter;

    /**
     * конструктор
     *
     * @param sentencesWriter объект для записи результата
     */
    public OccurencesFinder(SentencesWriter sentencesWriter) {
        this.sentencesWriter = sentencesWriter;
    }

    /**
     * Находит предложения, в которых встречаются искомые слова и
     * записывает их в файл
     *
     * @param sources список файлов, которые нужно проверить
     * @param words   список искомых слов
     * @param res     полное имя файла, куда нужно записать результат
     * @throws IOException при проблемах с открытием файлов и записью в файлы
     */
    public void getOccurences(String[] sources, String[] words, String res) throws InterruptedException, IOException {
        sentencesWriter.init(res);
        oldGoodMethod(sources, words);
    }

    /**
     * метод добавлен для тестов
     *
     * @param tLauncher мок-объект
     */
    void setThreadLauncher(ThreadLauncher tLauncher) {
        threadLauncher = tLauncher;
    }

    /**
     * Метод с ручным созданием потоков выполнения
     *
     * @param sources список файлов, которые нужно проверить
     * @param words   список искомых слов
     */
    private void oldGoodMethod(String[] sources, String[] words) throws InterruptedException {
        threadLauncher.clear();
        /*
        для каждого файла запускаем поток поиска слов
        (если файлов не больше, чем максимальное разрешенное количество потоков)
        в файлах FileReadThread
        */
        int curPos = 0;
        while (curPos < sources.length) {
            int countToLaunch = Integer.min(MAX_THREAD_COUNT, sources.length - curPos);

            for (int i = 0; i < countToLaunch; ++i) {
                threadLauncher.launch(
                        new FileReadThread(sources[curPos++], words, sentencesWriter));
            }

            threadLauncher.waitAllLaunched();
            threadLauncher.clear();
        }
    }

    @Override
    public void close() throws Exception {
        if (sentencesWriter != null) {
            sentencesWriter.close();
        }
    }
}
