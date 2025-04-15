package ru.t1.task3;

import java.util.LinkedList;
import java.util.concurrent.Executor;

public class ThreadPoolCustom implements Executor,Runnable {
    private final LinkedList<Runnable> tasks = new LinkedList<>();
    private volatile boolean isRunning = true;

    public ThreadPoolCustom(int nThreads) {
        for (int i = 0; i < nThreads; i++) {
            new Thread(this).start();
        }
    }

    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            tasks.offer(command);
        }
    }

    public void shutdown() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            Runnable task = tasks.poll();
            randomTimeOut();
            if (task != null) {
                task.run();
            }
        }
    }

    private void randomTimeOut() {
        try {
            Thread.sleep((long) (1000 * Math.random()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}