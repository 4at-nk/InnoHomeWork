package ru.inno.lec08HomeWork.ChatServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Поток ждущий подключения новых клиентов
 */
public class ListenerThread extends Thread {

    /**
     * сервер-сокет
     */
    private final ServerSocket serverSocket;

    public ListenerThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            Socket socket;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Не удалось создать клиент-сокет");
                return;
            }

            System.out.println(socket + " подключился.");

            /*
            авторизацию запускаем в потоке, авторизация пользователя
            не блокировала авторизацию следующих пользователей
            */
            Thread authorizationThread = new AuthorizationThread(socket);
            authorizationThread.start();
        }
    }
}
