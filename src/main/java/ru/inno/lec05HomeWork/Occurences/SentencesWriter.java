package ru.inno.lec05HomeWork.Occurences;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс для записи записи предложений
 *
 * @author FOAT
 * @version 1.0  05.02.2019
 */
class SentencesWriter implements AutoCloseable {

    /**
     * реализует запись
     */
    private BufferedWriter bufferedWriter;

    /**
     * Конструктор
     *
     * @param resFileName полное имя файла, который будет хранить результат
     * @throws IOException при проблемах с открытием файла resFileName на запись
     */
    SentencesWriter(String resFileName) throws IOException {
        bufferedWriter = new BufferedWriter(new FileWriter(resFileName, false));
    }

    /**
     * Конструктор для тестирования
     *
     * @param bufferedWriter мок для тестирования
     */
    SentencesWriter(BufferedWriter bufferedWriter) {
        if (bufferedWriter == null) {
            throw new NullPointerException();
        }

        this.bufferedWriter = bufferedWriter;
    }

    /**
     * Записывает предложение в файл
     *
     * @param sentence предложение, которое нужно записать
     * @throws IOException при проблемах при записи в файл
     */
    synchronized void write(String sentence) throws IOException {
        if (sentence == null) {
            throw new NullPointerException("На запись в файл подана null-строка");
        }

        bufferedWriter.write(sentence + "\r\n");
    }

    @Override
    public void close() throws IOException {
        if (bufferedWriter != null) {
            bufferedWriter.close();
        }
    }
}
