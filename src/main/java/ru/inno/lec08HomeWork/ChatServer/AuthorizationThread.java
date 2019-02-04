package ru.inno.lec08HomeWork.ChatServer;

import java.io.*;
import java.net.Socket;

/**
 * Поток регистрирующий новых клиентов
 */
public class AuthorizationThread extends Thread {

    /**
     * Клиент-сокет
     */
    private final Socket socket;

    public AuthorizationThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            os.write("Представьтесь: \n".getBytes());
            String userName = reader.readLine();

            // запускаем поток клиента
            Thread socketThread = new SocketThread(socket, userName);
            socketThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
