package ru.inno.lec03HomeWork.Boxes;

import java.util.*;

/**
 * Абстрактный класс для хранения коллекции Object'ов
 *
 * @author FOAT
 * @version 1.0  20.01.2019
 */
abstract public class ObjectBox {
    /*
     * методы addObject() и deleteObject() protected
     * для того, чтобы потомки определяли свои,
     * более строгие к параметрам, функции
     * добавления и удаления
     * */

    /** Уникальный идентификатор объекта */
    private UUID id = UUID.randomUUID();

    /**
     * Хранимая коллекция
     */
    private Collection<Object> collection;

    /**
     * Конструктор, который должны будут вызвать потомки класса
     *
     * @param arr        массив исходных Object'ов
     * @param collection ссылка на конкретную коллекцию (её реализация)
     */
    protected ObjectBox(Object[] arr, Collection<Object> collection) {
        if (collection == null) {
            throw new NullPointerException("Неинициализированная коллекция!");
        }

        if (arr == null) {
            throw new NullPointerException("Неинициализированный массив!");
        }

        this.collection = collection;
        collection.clear();
        Collections.addAll(collection, arr);
    }

    /**
     * Метод, с помощью которого потомки класса смогут обращаться к коллекции и
     * её содержимому
     *
     * @return ссылка на коллекцию
     */
    protected Collection<Object> getCollection() {
        return collection;
    }

    /**
     * Добавление элемента в коллекцию
     *
     * @param o ссылка на элемент
     * @return true - элемент добавлен, false - такой элемент уже имеется
     */
    protected boolean addObject(Object o) {
        if (o == null) {
            throw new NullPointerException("Неинициализированный объект!");
        }

        return collection.add(o);
    }

    /**
     * Удаления элемента из коллекции
     *
     * @param o ссылка на элемент
     * @return true - элемент удалён, false - такого элемента нет в коллекции
     */
    protected boolean deleteObject(Object o) {
        if (o == null) {
            throw new NullPointerException("Неинициализированный объект!");
        }

        return collection.remove(o);
    }

    /**
     * Метод возвращает строку из элементов коллекции
     *
     * @return строка элеметов коллекции
     */
    public String dump() {
        return collection.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectBox objectBox = (ObjectBox) o;
        return Objects.equals(id, objectBox.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ObjectBox{" +
                "id=" + id +
                ", collection=" + collection +
                '}';
    }
}
