package ru.inno.lec05HomeWork.Occurences;

import ru.inno.lec05HomeWork.Occurences.SentencesWriter.SentencesWriter;

import java.util.List;

/**
 * Поток для поиска предложений из файла, в которых встречается искомое слово
 *
 * @author FOAT
 * @version 1.0  05.02.2019
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
     * объект для запуска новых потоков
     */
    private ThreadLauncher threadLauncher = new ThreadLauncher();

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

    /**
     * метод добавлен для тестов
     *
     * @param threadLauncher мок-объект
     */
    void setThreadLauncher(ThreadLauncher threadLauncher) {
        this.threadLauncher = threadLauncher;
    }

    @Override
    public void run() {
        //открываем файл для чтения порциями
        try (FileChunkGetter fileChunkGetter = new FileChunkGetter(fileName)) {
            StringBuilder chunkString = new StringBuilder();
            //пока файл не закончился
            while (fileChunkGetter.getNextChunk(chunkString) != -1) {
                //преобразуем текст в список предложений
                List<String> sentences = SentencesSplitter.getSplittedText(chunkString.toString());
                //для каждого слова запускаем поток поиска слова
                //(если слов не больше, чем максимальное разрешенное количество потоков)
                //в предложениях WordFindThread
                int curPos = 0;
                while (curPos < words.length) {
                    int countToLaunch = Integer.min(MAX_THREAD_COUNT, words.length - curPos);

                    for (int i = 0; i < countToLaunch; ++i) {
                        threadLauncher.launch(
                                new WordFindThread(sentences, words[curPos++], sentencesWriter));
                    }

                    threadLauncher.waitAllLaunched();
                    threadLauncher.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}