/*
 * Фоат Шарафутдинов
 * Домашняя работа шестого занятия
 */

package ru.inno.lec06HomeWork;

import ru.inno.lec06HomeWork.Entities.Man;
import ru.inno.lec06HomeWork.Entities.Pet;
import ru.inno.lec06HomeWork.Entities.Toy;
import ru.inno.lec06HomeWork.JSSaver.JSonSerializer;
import ru.inno.lec06HomeWork.JSSaver.Serializer;

/**
 * Класс демонстрирующий работу JSON сериализатора
 *
 * @author FOAT
 * @version 1.0  23.01.2019
 */
public class lec06HWMain {

    public static void main(String[] args) throws Exception {

        String[] skills = {"swim", "draw", "biology"};
        String[] cars = {"bmw", "mercedez", "porsche", "ferrari"};

        Man man1 = new Man(Man.Sex.sMale, "Homer", 40, 100.0, skills,
                new Pet("barsik", 3, new Toy("bantik", 5.0)), 300.0,
                new Toy("ps4", 500.0), cars);

        Serializer jSonSerializer = new JSonSerializer();

        String file = ".\\tmp\\hw6\\man.json";
        jSonSerializer.serialize(man1, file);

        Man man2 = new Man();

        System.out.println("До сериализации");
        System.out.println("man1: " + man1);
        System.out.println("man2: " + man2);

        man2 = (Man) jSonSerializer.deSerialize(file);

        System.out.println();
        System.out.println("После сериализации");
        System.out.println("man1: " + man1);
        System.out.println("man2: " + man2);

        System.out.println();
        if (man1.equals(man2)) {
            System.out.println("Работает!");
        } else {
            System.out.println("Не работает");
        }
    }
}
