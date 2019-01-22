package ru.inno.lec05HomeWork.Occurences;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Класс для считывания из файла блоков текста с цельными предложениями
 *
 * @author FOAT
 * @version 1.0  22.01.2019
 */
public class FileChunkGetter implements AutoCloseable {

    /**
     * размер блока, котрый будет считываться из файла при первом подходе, байт
     */
    private static final int CHUNK_SIZE = 1024 * 1024 * 5;
    /**
     * максимальный размер буфера
     */
    private static final int CHUNK_MAX_SIZE = CHUNK_SIZE + 1024;
    /**
     * буфер
     */
    private static char[] buffer = new char[CHUNK_MAX_SIZE];
    /**
     * объект для считывания из файла
     */
    private BufferedReader bufferedReader;

    /**
     * Конструктор
     *
     * @param fileName полное имя файла, из которого будет производится чтение
     * @throws FileNotFoundException если файл не найден
     */
    FileChunkGetter(String fileName) throws FileNotFoundException {
        bufferedReader = new BufferedReader(new FileReader(fileName));
    }

    /**
     * Формирует текст из цельных предложений
     *
     * @param text ссылка на текст, который будет заполнен
     * @return количество прочитанных символов
     * @throws IOException проблемы открытия файла для чтения
     */
    int getNextChunk(StringBuilder text) throws IOException {
        //считываем первоначальное количество смволов
        int byteCount = bufferedReader.read(buffer, 0, CHUNK_SIZE);

        if (byteCount == -1) {
            return -1;
        }

        //если последний символ не символ конца предложения,
        //то считываем по-символьно до конца последнего предложения
        if (!isEndOfSentence(buffer[byteCount - 1])) {
            for (int pos = byteCount; ; ++pos) {
                if (pos == buffer.length) {
                    throw new IOException("Буфер переполнен!");
                }

                int nextSymbol = bufferedReader.read();
                if (nextSymbol == -1) {
                    break;
                }

                byteCount = pos + 1;
                buffer[pos] = (char) nextSymbol;
                if (isEndOfSentence(buffer[pos])) {
                    break;
                }
            }
        }

        text.setLength(0);
        text.append(String.copyValueOf(buffer, 0, byteCount));

        return byteCount;
    }

    /**
     * Проверяет, является ли символ концом предложения
     *
     * @param symbol проверяемый символ
     * @return true - символ конца предложения, false - не символ конца
     */
    private static boolean isEndOfSentence(char symbol) {
        final char[] ends = {'.', '?', '!', '…'};
        for (char ch : ends) {
            if (symbol == ch) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void close() throws Exception {
        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }
}
