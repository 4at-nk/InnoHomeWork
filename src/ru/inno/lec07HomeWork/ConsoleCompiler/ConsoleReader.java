package ru.inno.lec07HomeWork.ConsoleCompiler;

import java.util.Scanner;

/**
 * Класс для считывания многострочного текста из консоли
 */
public class ConsoleReader {

    /**
     * Функция начинает процесс считывания до ввода пустой строки
     *
     * @return введённый текст
     */
    public static String begin() {
        Scanner scanner = new Scanner(System.in);

        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if ("".equals(line)) {
                break;
            }

            stringBuilder.append(line + "\n");
        }

        return stringBuilder.toString();
    }
}
