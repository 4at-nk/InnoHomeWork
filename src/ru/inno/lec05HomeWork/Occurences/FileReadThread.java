package ru.inno.lec05HomeWork.Occurences;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Поток для поиска предложений из файла, в которых встречается искомое слово
 *
 * @author FOAT
 * @version 1.0  22.01.2019
 */
class FileReadThread extends Thread {
    /*
     * запускает для каждого слова свой поток обработки блока текста
     */

    /**
     * максимальное количество потоков WordFindThread
     */
    private final static int MAX_THREAD_COUNT = 3;
    /**
     * файл предложения из которого нужно проверить
     */
    private final String fileName;
    /**
     * список слов, которые нужно найти
     */
    private final String[] words;
    /**
     * объект для записи предложений в файл
     */
    private final SentencesWriter sentencesWriter;
    /**
     * Маска для нахождения предложения: Заглавная буква, символ конца предложения
     * и между ними сколь угодно любых символов не равных символу конца предложения
     */
    private final static String mask = "[A-ZА-ЯЁ][^.?!…]*?[.?!…]";
    /**
     * объект для работы с регулярными выражениями
     */
    private final static Pattern pattern = Pattern.compile(mask);

    /**
     * Конструктор
     *
     * @param fileName        файл предложения из которого нужно проверить
     * @param words           список слов, которые нужно найти
     * @param sentencesWriter объект для записи предложений в файл
     */
    FileReadThread(String fileName, String[] words, SentencesWriter sentencesWriter) {
        this.fileName = fileName;
        this.words = words;
        this.sentencesWriter = sentencesWriter;
    }

    @Override
    public void run() {
        //открываем файл для чтения порциями
        try (FileChunkGetter fileChunkGetter = new FileChunkGetter(fileName)) {
            StringBuilder chunkString = new StringBuilder();
            ArrayList<Thread> threads = new ArrayList<>();
            //пока файл не закончился
            while (fileChunkGetter.getNextChunk(chunkString) != -1) {
                //преобразуем текст в список предложений
                ArrayList<String> sentences = getSplittedText(chunkString.toString());

                //для каждого слова запускаем поток поиска слова
                //(если слов не больше, чем максимальное разрешенное количество потоков)
                //в предложениях WordFindThread
                int curPos = 0;
                while (curPos < words.length) {
                    int countToLaunch = Integer.min(MAX_THREAD_COUNT, words.length - curPos);

                    for (int i = 0; i < countToLaunch; ++i) {
                        threads.add(new WordFindThread(sentences, words[curPos++], sentencesWriter));
                        threads.get(i).start();
                    }

                    for (Thread thread : threads) {
                        thread.join();
                    }

                    threads.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Разделяет текст на предложения
     *
     * @param text исходный текст
     * @return список предложений
     */
    private ArrayList<String> getSplittedText(String text) {
        Matcher matcher = pattern.matcher(text);
        ArrayList<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(text.substring(matcher.start(), matcher.end()));
        }

        return result;
    }
}