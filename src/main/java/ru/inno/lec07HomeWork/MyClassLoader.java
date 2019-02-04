package ru.inno.lec07HomeWork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Класслодер
 */
public class MyClassLoader extends ClassLoader {

    /**
     * Имя класса
     */
    private String className;

    /**
     * Путь к байт-коду
     */
    private String byteCodeFileName;

    public MyClassLoader(String className, String byteCodeFileName) {
        this.className = className;
        this.byteCodeFileName = byteCodeFileName;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (className.equals(name)) {
            byte[] bytes = new byte[0];
            try {
                bytes = Files.readAllBytes(Paths.get(byteCodeFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return defineClass(name, bytes, 0, bytes.length);
        }
        return super.findClass(name);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (className.equals(name)) {
            return findClass(name);
        }
        return super.loadClass(name);
    }
}
