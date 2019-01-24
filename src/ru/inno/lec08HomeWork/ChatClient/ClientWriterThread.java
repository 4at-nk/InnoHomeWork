package ru.inno.lec08HomeWork.ChatClient;

import ru.inno.lec08HomeWork.ChatServer.ChatServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Поток исходящих записи сообщений
 */
public class ClientWriterThread extends Thread {

    /**
     * Сокет
     */
    private final Socket socket;

    public ClientWriterThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            OutputStream os = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);

            String line;
            while ((line = scanner.nextLine()) != null) {
                os.write((line + "\n").getBytes());

                // выход из чата
                if (ChatServer.stopWord.equals(line)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
