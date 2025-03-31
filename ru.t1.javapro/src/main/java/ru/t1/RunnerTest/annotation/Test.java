package ru.t1.RunnerTest.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

public @interface Test {

    int priority() default 5;

}
