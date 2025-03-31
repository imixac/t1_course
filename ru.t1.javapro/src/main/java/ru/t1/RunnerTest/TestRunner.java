package ru.t1.RunnerTest;

import ru.t1.RunnerTest.annotation.AfterSuite;
import ru.t1.RunnerTest.annotation.BeforeSuite;
import ru.t1.RunnerTest.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class TestRunner {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ExampleClassForTest exampleClassForTest = new ExampleClassForTest();
        runTest(exampleClassForTest.getClass());
    }

    private static void checkBeforeAndAfterTest(Method[] methods) {
        int countBeforeSuite = 0;
        int countAfterSuite = 0;
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(BeforeSuite.class)) {
                    countBeforeSuite++;
                    if (countBeforeSuite > 1) {
                        throw new RuntimeException("Аннотации countBeforeSuite больше 1 !!!");
                    }
                }
                if (annotation.annotationType().equals(AfterSuite.class)) {
                    countAfterSuite++;
                    if (countAfterSuite > 1) {
                        throw new RuntimeException("Аннотации countAfterSuite больше 1 !!!");
                    }
                }
            }
        }
    }

    private static <T> void getBeforeSuite(Method[] methods, Class<T> c) {
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(BeforeSuite.class)) {
                    try {
                        method.invoke(c);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private static <T> void getAfterSuite(Method[] methods, Class<T> c) {
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(AfterSuite.class)) {
                    try {
                        method.invoke(c);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private static <T> void runTest(Class<T> c) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method[] methods = c.getDeclaredMethods();
        Constructor<T> constructor = c.getDeclaredConstructor();
        T t = constructor.newInstance();
        checkBeforeAndAfterTest(methods);

//        Запуск BeforeSuite
        getBeforeSuite(methods, c);

//        Запуск Test
        Queue<Method> queueTests = new PriorityQueue<>(
                (m1, m2) -> Integer.compare(m2.getDeclaredAnnotation(Test.class).priority(),
                        m1.getDeclaredAnnotation(Test.class).priority())
        );

        for (Method method : methods) {
            if (method.getDeclaredAnnotation(Test.class) != null) {
                queueTests.add(method);
            }
        }

        Method method;
        while ((method = queueTests.poll()) != null) {
            method.invoke(t);
        }

//        Запуск AfterSuite
        getAfterSuite(methods, c);
        System.out.println("-------------\nThe end!");
    }


}
