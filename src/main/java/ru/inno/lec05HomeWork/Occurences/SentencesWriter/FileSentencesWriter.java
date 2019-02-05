package ru.inno.lec05HomeWork.Occurences.SentencesWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Класс для записи записи предложений
 *
 * @author FOAT
 * @version 1.0  05.02.2019
 */
public class FileSentencesWriter implements SentencesWriter, AutoCloseable {

    /**
     * реализует запись
     */
    private Writer writer;

    public FileSentencesWriter() { }

    /**
     * Конструктор для тестирования
     *
     * @param writer мок для тестирования
     */
    public FileSentencesWriter(FileWriter writer) {
        if (writer == null) {
            throw new NullPointerException();
        }

        this.writer = writer;
    }

    @Override
    public void init(String destinationFileName) throws IOException {
        close();
        writer = new BufferedWriter(new FileWriter(destinationFileName, false));
    }

    /**
     * Записывает предложение в файл
     *
     * @param sentence предложение, которое нужно записать
     * @throws IOException при проблемах при записи в файл
     */
    @Override
    synchronized public void write(String sentence) throws IOException {
        if (sentence == null) {
            throw new NullPointerException("На запись в файл подана null-строка");
        }

        writer.write(sentence + "\r\n");
    }

    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}
