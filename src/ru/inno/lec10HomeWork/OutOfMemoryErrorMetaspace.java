package ru.inno.lec10HomeWork;

import ru.inno.lec07HomeWork.MyClassLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс демонстрирующий исключение OutOfMemoryError: Metaspace
 *
 * Для того, чтобы не ждать долго переполнения Metaspace, нужно
 * установить максмальный размер Metaspace в опциях, например:
 * VM options: -XX:MaxMetaspaceSize=10m
 */
public class OutOfMemoryErrorMetaspace {
    /**
     * Имя загружаемого класса
     */
    private static final String className = "ru.inno.lec07HomeWork.Entities.SomeClass";

    /**
     * Путь к скомпилированному байт-коду
     */
    private static final String byteCodeFileName = "./tmp/hw7/SomeClass.class";

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException {
        List<Class> classList = new ArrayList<>();
        for (;;) {
            ClassLoader clazz = new MyClassLoader(className, byteCodeFileName);
            Class<?> someClassClass = clazz.loadClass(className);
            classList.add(someClassClass);
            Thread.sleep(1);
        }
    }
}
