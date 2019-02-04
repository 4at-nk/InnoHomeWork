/*
 * Фоат Шарафутдинов
 * Домашняя работа восьмого занятия
 */

package ru.inno.lec08HomeWork.ChatServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

/**
 * Класс реализующий сервер чата
 */
public class ChatServer {

    /**
     * Занимаемый порт
     */
    public static final int PORT = 3232;

    /**
     * Слово для выхода из чата
     */
    public static final String stopWord = "quit";

    public static void main(String[] args) throws InterruptedException {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Не удалось создать сокет-сервер!");
            return;
        }

        System.out.println("Сервер создан. Порт: " + PORT);

        //поток для ожидания клиентов
        Thread listenerThread = new ListenerThread(serverSocket);
        listenerThread.start();

        // делаем возможность серверу писать сообщения
        Scanner scanner = new Scanner(System.in);
        String line;
        while ((line = scanner.nextLine()) != null) {
            if ("".equals(line)) {
                continue;
            }

            SocketThread.writeToAll(line, "[сервер]");
        }

        listenerThread.join();

        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Не удалось закрыть сокет-сервер!");
            return;
        }

        System.out.println("Сервер закрыт");
    }
}
