package ru.t1.task1.RunnerTest;

import ru.t1.task1.RunnerTest.annotation.BeforeSuite;
import ru.t1.task1.RunnerTest.annotation.Test;

public class ExampleClassForTest {

    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("Запуск BeforeSuite");
    }

    @Test(priority = 3)
    public void test1() {
        System.out.println("Запуск Test1, priority = 3");
    }

    @Test(priority = 4)
    public void test2() {
        System.out.println("Запуск Test2, priority = 4");
    }

    @Test(priority = 1)
    public void test() {
        System.out.println("Запуск Test, priority = 1");
    }

//    @AfterSuite
    public static void afterSuite() {
        System.out.println("Запуск AfterSuite");
    }

//    @AfterSuite
    public static void afterSuite2() {
        System.out.println("Запуск AfterSuite");
    }
}
