/*
 * Фоат Шарафутдинов
 * Домашняя работа третьего занятия
 */

package ru.inno.lec03HomeWork;

import ru.inno.lec03HomeWork.Boxes.MathBox;

/**
 * Класс демонстрирующий работу класса MathBox<>
 *
 * @author FOAT
 * @version 1.0  18.01.2019
 */
public class Main {

    public static void main(String[] args) {
        Integer[] iArr = {
                10, 8, 6, 6, 10, 10, 10, 5, -5, 5, 1, 7, 4, 3, 3, 1, 2, 9
        };
        Double[] dArr = new Double[iArr.length];
        Float[] fArr = new Float[iArr.length];

        for (int i = 0; i < iArr.length; ++i) {
            dArr[i] = iArr[i].doubleValue();
            fArr[i] = iArr[i].floatValue();
        }

        MathBox<Integer> mbInteger = new MathBox<>(iArr);
        MathBox<Double> mbDouble = new MathBox<>(dArr);
        MathBox<Float> mbFloat = new MathBox<>(fArr);

        System.out.println("Содержимое mbInteger: " + mbInteger.dump());
        System.out.println("Содержимое mbDouble: " + mbDouble.dump());
        System.out.println("Содержимое mbFloat: " + mbFloat.dump());
        System.out.println();

        System.out.println("Сумма элементов mbInteger: " + mbInteger.summator());
        System.out.println("Сумма элементов mbDouble: " + mbDouble.summator());
        System.out.println("Сумма элементов mbFloat: " + mbFloat.summator());
        System.out.println();

        final int DIVIDER = 4;
        System.out.println("Элементы mbInteger после деления на 4: "
                + mbInteger.splitter(DIVIDER));
        System.out.println("Элементы mbDouble после деления на 4: "
                + mbDouble.splitter(DIVIDER));
        System.out.println("Элементы mbFloat после деления на 4: "
                + mbFloat.splitter(DIVIDER));
        System.out.println();

        final int TO_REMOVE_1 = 3;
        final int TO_REMOVE_2 = 10;
        mbInteger.remove(TO_REMOVE_1);
        mbInteger.remove(TO_REMOVE_2);
        mbDouble.remove((double) TO_REMOVE_1);
        mbDouble.remove((double) TO_REMOVE_2);
        mbFloat.remove((float) TO_REMOVE_1);
        mbFloat.remove((float) TO_REMOVE_2);
        System.out.println("Удаляем из коллекций значения 3 и 10.");
        System.out.println("Элементы mbInteger после удаления элементов: "
                + mbInteger.dump());
        System.out.println("Элементы mbDouble после удаления элементов: "
                + mbDouble.dump());
        System.out.println("Элементы mbFloat после удаления элементов: "
                + mbFloat.dump());
        System.out.println();

        final int TO_ADD_1 = 42;
        final int TO_ADD_2 = 0;
        mbInteger.add(TO_ADD_1);
        mbInteger.add(TO_ADD_2);
        mbDouble.add((double) TO_ADD_1);
        mbDouble.add((double) TO_ADD_2);
        mbFloat.add((float) TO_ADD_1);
        mbFloat.add((float) TO_ADD_2);
        System.out.println("Добавляем в коллекции значения 0 и 42.");
        System.out.println("Элементы mbInteger после добавления элементов: "
                + mbInteger.dump());
        System.out.println("Элементы mbDouble после добавления элементов: "
                + mbDouble.dump());
        System.out.println("Элементы mbFloat после добавления элементов: "
                + mbFloat.dump());
    }
}
