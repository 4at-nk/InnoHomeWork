/*
 * Фоат Шарафутдинов
 * Домашняя работа седьмого занятия
 */

package ru.inno.lec07HomeWork;

import ru.inno.lec07HomeWork.ConsoleCompiler.ConsoleCompiler;
import ru.inno.lec07HomeWork.Entities.SomeClass;
import ru.inno.lec07HomeWork.Entities.Worker;

import java.util.Scanner;

public class lec07HomeWorkMain {

    /**
     * Имя загружаемого класса
     */
    private static final String className = "ru.inno.lec07HomeWork.Entities.SomeClass";

    /**
     * Путь к исходнику, который будет компилироваться
     */
    private static final String codeFileName = "./tmp/hw7/SomeClass.java";

    /**
     * Путь к скомпилированному байт-коду
     */
    private static final String byteCodeFileName = "./tmp/hw7/SomeClass.class";

    public static void main(String[] args) throws Exception {
        ConsoleCompiler.start(codeFileName);

        ClassLoader clazz = new MyClassLoader(className, byteCodeFileName);
        Class<?> someClassClass = clazz.loadClass(className);
        Worker worker = (Worker) someClassClass.newInstance();
        Worker workerNormal = new SomeClass();

        Scanner scanner = new Scanner(System.in);
        int a = 0, b = 0;

        try {
            System.out.print("Введите a: ");
            a = scanner.nextInt();
            System.out.print("Введите b: ");
            b = scanner.nextInt();
        } catch (Exception e) {
            throw new Exception("Некорректные значения");
        }

        System.out.println();
        System.out.println("Результат введённого кода: " + worker.doWork(a, b));
        System.out.println("Результат старого кода: " + workerNormal.doWork(a, b));
    }
}
