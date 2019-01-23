package ru.inno.lec06HomeWork.JSSaver;

import java.io.IOException;

/**
 * Интерфейс для сериализации объектов
 */
public interface Serializer {
    /**
     * Сериализует объект в файл
     *
     * @param object объект для сериализации
     * @param file   имя файла, в котором сохранится сериализация
     * @throws IOException при проблемах при открытии файла на запись
     */
    void serialize(Object object, String file) throws IOException;

    /**
     * Десериализирует объект из файла
     *
     * @param file имя файла, в котором хранится сериальзация
     * @return десериализованный объект
     * @throws Exception при проблемах при открытии файла на чтение и несоответствии типов
     */
    Object deSerialize(String file) throws Exception;
}
