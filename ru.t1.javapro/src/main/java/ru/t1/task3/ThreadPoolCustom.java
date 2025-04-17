package ru.t1.task3;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPoolCustom {
    private final Object monitor = new Object();
    private final Thread[] threads;
    private final LinkedList<Runnable> tasks = new LinkedList<>();
    private AtomicBoolean isRunning = new AtomicBoolean(true);

    public ThreadPoolCustom(int nThreads) {
        if (nThreads <= 0) {
            throw new IllegalArgumentException("Размер пула должен быть больше 0");
        }
        threads = new Thread[nThreads];
        for (int i = 0; i < nThreads; i++) {
            Runnable runnable = () -> {
                while (isRunning.get()) {
                    Runnable task = tasks.poll();
                    randomTimeOut();
                    if (task != null) {
                        task.run();
                    }
                }
            };
            threads[i] = new Thread(runnable);
            threads[i].start();
        }
    }

    public void execute(Runnable command) {
        synchronized (monitor) {
            if (isRunning.get()) {
                tasks.offer(command);
            }
            monitor.notify();
        }
    }

    public void shutdown() {
        isRunning.set(false);
    }

    private void randomTimeOut() {
        try {
            Thread.sleep((long) (1000 * Math.random()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}