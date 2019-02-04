package ru.inno.lec08HomeWork.ChatServer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Поток клиента
 */
public class SocketThread extends Thread {

    /**
     * Клиент-сокет
     */
    private final Socket socket;

    /**
     * Имя пользователя, которое он ввёл при регистрации
     */
    private final String userName;

    /**
     * Монитор для работы с общим списком клиентов
     */
    private static final Object lock = new Object();

    /**
     * Общий список клиент-сокетов
     */
    private static final List<Socket> socketList = new ArrayList<>();

    /**
     * Конструктор потока
     *
     * @param socket   клиент-сокет
     * @param userName имя пользователя
     */
    public SocketThread(Socket socket, String userName) {
        this.socket = socket;
        this.userName = userName;

        synchronized (lock) {
            socketList.add(socket);
        }

        writeToAll("К чату присоединился " + userName + "...", "");
    }

    /**
     * Удаление сокета из общего списка
     *
     * @param socket сокет
     */
    private void deleteSocket(Socket socket) {
        synchronized (lock) {
            socketList.remove(socket);
        }
    }

    /**
     * Пишет сообщение всем зарегистрированным пользователям
     *
     * @param text текст сообщения
     * @param name имя клиента
     */
    static void writeToAll(String text, String name) {
        for (Socket sock : socketList) {
            try {
                String message = text;
                if (!"".equals(name)) {
                    message = name + ": " + text;
                }

                OutputStream os = sock.getOutputStream();
                os.write((message + "\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                if ("".equals(line)) {
                    continue;
                }

                // выход из чата
                if (ChatServer.stopWord.equals(line)) {
                    writeToAll(userName + " вышел из чата...", "");
                    break;
                }

                writeToAll(line, userName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            deleteSocket(socket);
        }
    }
}
