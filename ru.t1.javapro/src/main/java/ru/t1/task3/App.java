package ru.t1.task3;

/*Попробуйте реализовать собственный пул потоков.
+В качестве аргументов конструктора пулу передается его емкость (количество рабочих потоков).
Как только пул создан, он сразу инициализирует и запускает потоки.
Внутри пула очередь задач на исполнение организуется через LinkedList<Runnable>.
При выполнении у пула потоков метода execute(Runnabler), указанная задача должна попасть в очередь исполнения,
и как только появится свободный поток – должна быть выполнена.

Также необходимо реализовать метод shutdown(),
после выполнения которого новые задачи больше не принимаются пулом (при попытке добавить задачу можно бросать IllegalStateException),
и все потоки для которых больше нет задач завершают свою работу. Дополнительно можно добавить метод awaitTermination() без таймаута,
работающий аналогично стандартным пулам потоков
* */

public class App {
    public static void main(String[] args) {
        ThreadPoolCustom threadPoolCustom = new ThreadPoolCustom(4);
        RandomCounter counter = new RandomCounter();
        for (int i = 0; i < 1000; i++) {
            threadPoolCustom.execute(() -> {
                counter.getRandom();
            });
            }
        try {
            Thread.sleep(10000 );
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        threadPoolCustom.shutdown();
        System.out.println("THE END");
    }

    public static class RandomCounter {
        public Double getRandom() {
            Double rand = (Math.random() * 1000);
            System.out.println(Thread.currentThread().getName() + ": " + rand);
            return rand;
        }
    }
}
