package ru.inno.lec05HomeWork.Occurences;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс для записи записи предложений
 *
 * @author FOAT
 * @version 1.0  22.01.2019
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
     * Записывает предложение в файл
     *
     * @param sentence предложение, которое нужно записать
     * @throws IOException при проблемах при записи в файл
     */
    synchronized void write(String sentence) throws IOException {
        bufferedWriter.write(sentence + "\r\n");
    }

    @Override
    public void close() throws IOException {
        if (bufferedWriter != null) {
            bufferedWriter.close();
        }
    }
}
