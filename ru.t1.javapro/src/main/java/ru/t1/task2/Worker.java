package ru.t1.task2;

import ru.t1.task2.enums.Profession;

public class Worker {
    private String name;
    private int age;
    private Profession profession;

    public Worker(String name, int age, Profession profession) {
        this.name = name;
        this.age = age;
        this.profession = profession;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Profession getProfession() {
        return profession;
    }
}
