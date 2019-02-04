package ru.inno.lec08HomeWork.ChatClient;

import ru.inno.lec08HomeWork.ChatServer.ChatServer;

import java.io.*;
import java.net.Socket;

/**
 * Класс реализующий клиентскую часть чата
 */
public class ChatClient {

    /**
     * Хост
     */
    public static final String host = "127.0.0.1";

    public static void main(String[] args) throws InterruptedException {
        try (Socket socket = new Socket(host, ChatServer.PORT)) {
            // запускаем поток чтения сообщений
            Thread readerThread = new ClientReaderThread(socket);
            readerThread.start();
            // запускаем поток записи сообщений
            Thread writeThread = new ClientWriterThread(socket);
            writeThread.start();
            writeThread.join();
        } catch (IOException e) {
            System.out.println("Не удалось подключиться к серверу!");
        }
    }
}
