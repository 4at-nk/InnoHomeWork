package ru.inno.lec06HomeWork.Entities;

import java.util.Objects;

/**
 * Класс "Питомец" для демонстрации сериализации
 */
public class Pet {
    private String name = "";
    private int age = 0;
    private Toy toy;

    public Pet() {
    }

    public Pet(String name, int age, Toy toy) {
        this.name = name;
        this.age = age;
        this.toy = toy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return age == pet.age &&
                Objects.equals(name, pet.name) &&
                Objects.equals(toy, pet.toy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, toy);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", toy=" + toy +
                '}';
    }
}
