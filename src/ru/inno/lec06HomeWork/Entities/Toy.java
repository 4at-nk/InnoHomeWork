package ru.inno.lec06HomeWork.Entities;

import java.util.Objects;

/**
 * Класс "Игрушка" для демонстрации сериализации
 */
public class Toy {

    private String name = "";
    private double cost = 0.0;

    public Toy() {
    }

    public Toy(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Toy toy = (Toy) o;
        return Double.compare(toy.cost, cost) == 0 &&
                Objects.equals(name, toy.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost);
    }

    @Override
    public String toString() {
        return "Toy{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
