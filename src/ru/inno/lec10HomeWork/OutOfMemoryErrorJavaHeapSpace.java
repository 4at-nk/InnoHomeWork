package ru.inno.lec10HomeWork;

/**
 * Класс демонстрирующий исключение OutOfMemoryError: Java heap space
 */
public class OutOfMemoryErrorJavaHeapSpace {

    public static void main(String[] args) throws InterruptedException {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; ; ++i) {
            // на каждом шаге удваиваем и расширяем строку
            stringBuilder.append(stringBuilder.toString() + "12345");
            Thread.sleep(100);
            // на каждом втором шаге итерации сокращаем строку вдвое, создавая работу для GC
            if (i % 2 == 0) {
                stringBuilder.setLength(stringBuilder.length() / 2);
            }
            System.out.println(stringBuilder.length());
        }
    }
}
