package ru.inno.lec05HomeWork.Occurences;

import java.io.IOException;
import java.util.ArrayList;

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
    private final ArrayList<String> sentences;
    /**
     * слово, которое нужно найти
     */
    private final String word;
    /**
     * объект для записи предложений в файл
     */
    private final SentencesWriter sentencesWriter;

    /**
     * Конструктор
     *
     * @param sentences       список предложений, которые нужно проверить
     * @param word            слово, которое нужно найти
     * @param sentencesWriter объект для записи предложений в файл
     */
    WordFindThread(ArrayList<String> sentences, String word, SentencesWriter sentencesWriter) {
        this.sentences = sentences;
        this.word = word;
        this.sentencesWriter = sentencesWriter;
    }

    @Override
    public void run() {
        ArrayList<String> res = WordFinder.find(sentences, word);
        for (String s : res) {
            try {
                sentencesWriter.write(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
