package edu.hw8.task2;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FixedThreadPool implements ThreadPool {
    private final BlockingQueue<Runnable> taskQueue;
    private final Thread[] threads;
    private volatile boolean isRunning = false;
    private final int capacity = 16;

    private FixedThreadPool(int size) {
        taskQueue = new ArrayBlockingQueue<>(capacity, true);

        threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = (new TaskWorker());
        }
    }

    public static FixedThreadPool create(int nThread) {
        return new FixedThreadPool(nThread);
    }

    @Override
    public void start() {
        isRunning = true;
        Arrays.stream(threads).forEach(Thread::start);
    }

    @Override
    public void execute(Runnable newTask) {
        taskQueue.add(newTask);
    }

    @Override
    public void close() {
        synchronized (taskQueue) {
            try {
                taskQueue.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
        isRunning = false;
    }

    private void notifyTaskQueue() {
        synchronized (taskQueue) {
            taskQueue.notify();
        }
    }

    private class TaskWorker extends Thread {
        @Override
        public void run() {
            while (isRunning) {
                if (taskQueue.isEmpty()) {
                    notifyTaskQueue();
                }
                Runnable nextTask = taskQueue.poll();
                if (nextTask != null) {
                    nextTask.run();
                }
                Thread.yield();
            }
        }
    }
}
