package ru.inno.lec06HomeWork.Entities;

import java.util.Arrays;
import java.util.Objects;

/**
 * Класс "Человек" для демонстрации сериализации
 */
public class Man {
    public enum Sex {sMale, sFemale, sUnknown}

    private Sex sex = Sex.sUnknown;
    private String name = "";
    private int age = 0;
    private double weight = 0.0;
    private String[] skills;
    private Pet pet;
    private double cash = 0.0;
    private Toy toy;
    private String[] cars;

    public Man() {

    }

    public Man(Sex sex, String name, int age, double weight, String[] skills, Pet pet, double cash, Toy toy, String[] cars) {
        this.sex = sex;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.skills = skills;
        this.pet = pet;
        this.cash = cash;
        this.toy = toy;
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Man man = (Man) o;
        return age == man.age &&
                Double.compare(man.weight, weight) == 0 &&
                Double.compare(man.cash, cash) == 0 &&
                sex == man.sex &&
                Objects.equals(name, man.name) &&
                Arrays.equals(skills, man.skills) &&
                Objects.equals(pet, man.pet) &&
                Objects.equals(toy, man.toy) &&
                Arrays.equals(cars, man.cars);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(sex, name, age, weight, pet, cash, toy);
        result = 31 * result + Arrays.hashCode(skills);
        result = 31 * result + Arrays.hashCode(cars);
        return result;
    }

    @Override
    public String toString() {
        return "Man{" +
                "sex=" + sex +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", skills=" + Arrays.toString(skills) +
                ", pet=" + pet +
                ", cash=" + cash +
                ", toy=" + toy +
                ", cars=" + Arrays.toString(cars) +
                '}';
    }
}
