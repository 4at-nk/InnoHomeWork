package ru.inno.lec08HomeWork.ChatClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Поток чтения входящих сообщений
 */
public class ClientReaderThread extends Thread {

    /**
     * сокет
     */
    private final Socket socket;

    public ClientReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            while (!isInterrupted()) {
                if (socket.isClosed()) {
                    break;
                }

                String line;
                if ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
