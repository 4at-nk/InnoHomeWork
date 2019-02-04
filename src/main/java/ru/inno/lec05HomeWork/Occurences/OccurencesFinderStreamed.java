package ru.inno.lec05HomeWork.Occurences;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Класс реализующий функционал OccurencesFinder
 * с использованием StreamAPI
 */
public class OccurencesFinderStreamed {

    /**
     * Находит предложения, в которых встречаются искомые слова и
     * записывает их в файл
     *
     * @param sources список файлов, которые нужно проверить
     * @param words   список искомых слов
     * @param res     полное имя файла, куда нужно записать результат
     * @throws IOException при проблемах с открытием файлов и записью в файлы
     */
    public static void getOccurences(String[] sources, String[] words, String res) throws IOException {
        //открываем файл для записи результата
        try (SentencesWriter sentencesWriter = new SentencesWriter(res)) {
            newStreamedMethod(sources, words, sentencesWriter);
        }
    }

    /**
     * Метод с использованием Stream API
     *
     * @param sources         список файлов, которые нужно проверить
     * @param words           список искомых слов
     * @param sentencesWriter объект для записи предложений в файл
     */
    private static void newStreamedMethod(String[] sources, String[] words,
                                          SentencesWriter sentencesWriter) {
        Arrays.stream(sources)
                .parallel()
                .map(fileName -> {      //параллельно для каждого файла создаём свой FileChunkGetter
                    try {
                        return new FileChunkGetter(fileName);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .forEach(fileChunkGetter -> {
                    StringBuilder chunkString = new StringBuilder();
                    try {
                        // пока файл не закончится
                        while (fileChunkGetter.getNextChunk(chunkString) != -1) {

                            // из следующего блока текста формируем список предложений
                            List<String> sentences =
                                    SentencesSplitter.getSplittedText(chunkString.toString());

                            // делаем параллельный для каждого слова поиск в списке предложений
                            Arrays.stream(words)
                                    .parallel()
                                    .forEach(word -> {
                                        List<String> goodSentences =
                                                WordFinder.find(sentences, word);

                                        // записываем подходящие предложения
                                        goodSentences.stream()
                                                .forEach(sentence -> {
                                                    try {
                                                        sentencesWriter.write(sentence);
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                });
                                    });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
