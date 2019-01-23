package ru.inno.lec07HomeWork.Entities;

public class SomeClass implements Worker {

    //должен идти последним, иначе всё, что ниже обрубится
    @Override
    public int doWork(int a, int b) {
return 1;
}
}