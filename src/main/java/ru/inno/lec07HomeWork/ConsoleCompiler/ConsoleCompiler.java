package ru.inno.lec07HomeWork.ConsoleCompiler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;

/**
 * Класс для компилляции файла с введённым кодом из консоли
 */
public class ConsoleCompiler {

    /**
     * Компилирует файл в байт-код с помощью javac
     *
     * @param file компилируемый исходник
     */
    private static void compile(String file) {
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();

        String[] javacOpts = {file};

        javac.run(null, null, null, javacOpts);
    }

    /**
     * Формирует файл для компилляции
     *
     * @param file файл исходников с пустым методом doWork()
     * @param code код введённый из консоли
     */
    private static void makeFile(String file, String code) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            boolean methodIsInFlag = false;
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");

                if (line.contains("public int doWork(int a, int b) {")) {
                    stringBuilder.append(code).append("}\n}");
                    methodIsInFlag = true;
                    break;
                }
            }

            if (!methodIsInFlag) {
                throw new Exception("В файле " + file + " не найден метод doWork()");
            }
        }

        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(stringBuilder.toString());
        }
    }

    /**
     * Запускает ввод кода в консоль и компилирует его
     *
     * @param file файл исходников с пустым методом doWork()
     */
    public static void start(String file) throws Exception {
        System.out.println("Для завершения ввода введите пустую строку.");
        System.out.println("Вводите код:");

        String code = ConsoleReader.begin();

        makeFile(file, code);

        compile(file);
    }
}
