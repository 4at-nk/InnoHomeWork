package ru.inno.lec05HomeWork.Occurences;

import ru.inno.lec05HomeWork.Occurences.SentencesWriter.SentencesWriter;

import java.io.IOException;
import java.util.List;

/**
 * Поток для поиска строк, в которых встречается искомое слово, и
 * их записи в файл
 *
 * @author FOAT
 * @version 1.0  22.01.2019
 */
class WordFindThread extends Thread {

    /**
     * список предложений, которые нужно проверить
     */
    private final List<String> sentences;
    /**
     * слово, которое нужно найти
     */
    private final String word;
    /**
     * объект для записи предложений
     */
    private final SentencesWriter sentencesWriter;

    /**
     * Конструктор
     *
     * @param sentences       список предложений, которые нужно проверить
     * @param word            слово, которое нужно найти
     * @param sentencesWriter объект для записи предложений
     */
    WordFindThread(List<String> sentences, String word, SentencesWriter sentencesWriter) {
        this.sentences = sentences;
        this.word = word;
        this.sentencesWriter = sentencesWriter;
    }

    @Override
    public void run() {
        List<String> res = WordFinder.find(sentences, word);
        for (String s : res) {
            try {
                sentencesWriter.write(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
