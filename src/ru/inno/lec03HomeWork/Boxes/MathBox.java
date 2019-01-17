package ru.inno.lec03HomeWork.Boxes;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Класс для хранения упорядоченной по возрастанию коллекции уникальных чисел.
 *
 * @author FOAT
 * @version 1.0  18.01.2019
 */
public class MathBox<T extends Number> extends ObjectBox {

    /**
     * Конструктор
     *
     * @param arr массив чисел нужного типа
     */
    public MathBox(T[] arr) {
        super(arr, new TreeSet<Object>());
    }

    /**
     * Метод, возвращающий сумму всех элементов коллекции
     *
     * @return сумма элементов
     */
    public double summator() {
        double sum = 0.0;

        Iterator it = getCollection().iterator();
        while (it.hasNext()) {
            T t = (T) it.next();
            sum += t.doubleValue();
        }

        return sum;
    }

    /**
     * Метод, возвращающий коллекцию, являющуюся результатом деления хранимой
     * коллекции на делитель
     *
     * @param divider делитель
     * @return поделённая коллекция
     */
    public TreeSet<Double> splitter(double divider) {
        TreeSet<Double> res = new TreeSet<>();

        Iterator it = getCollection().iterator();
        while (it.hasNext()) {
            T t = (T) it.next();
            res.add(t.doubleValue() / divider);
        }

        return res;
    }

    /**
     * Метод, удаляющий число из коллекции
     *
     * @param t число, которое нужно удалить
     * @return true - число удалёно, false - такого числа нет в коллекции
     */
    public boolean remove(T t) {
        /* тип удаляемого элемента должен точно соответствовать типу хранимому в коллекции, поэтому T */
        return super.deleteObject(t);
    }

    /**
     * Метод, добавляющий число в коллекцию
     *
     * @param t число, которое нужно добавить
     * @return true - число добавлено, false - такое число уже есть в коллекции
     */
    public boolean add(T t) {
        /* тип добавляемого элемента должен точно соответствовать типу хранимому в коллекции, поэтому T */
        return super.addObject(t);
    }
}
