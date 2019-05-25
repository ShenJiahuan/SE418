package com.shenjiahuan.task4;


import org.apache.log4j.Logger;

import java.util.concurrent.FutureTask;
import java.util.Date;

public class ThreadPool {
    final static Logger logger = Logger.getLogger(ThreadPool.class);

    private int nThreads;
    private int threshold;
    private int upperBound;
    private int timeout;
    private PoolWorker[] threads;
    private BlockingTwoWayQueue queue;

    public ThreadPool(int nThreads, int threshold, int upperBound, int timeout) {
        this.nThreads = nThreads;
        this.threshold = threshold;
        this.upperBound = upperBound;
        this.timeout = timeout;
        queue = new BlockingTwoWayQueue(threshold);
        threads = new PoolWorker[nThreads];

        for (int i = 0; i < nThreads; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
        new PoolCleaner().start();
    }

    public void execute(FutureTask<Object> task) {
        synchronized (queue) {
            if (queue.size() > upperBound) {
                logger.debug("[Reject] Create time: " + new Date());
                task.cancel(true);
                return;
            }
            Long time = (System.currentTimeMillis() / 1000L);
            Pair pair = new Pair(task, time);
            queue.add(pair);
            queue.notify();
        }
    }

    private class PoolWorker extends Thread {
        public void run() {
            Pair pair;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                        }
                    }
                    pair = (Pair) queue.poll();
                }
                // If we don't catch RuntimeException,
                // the pool could leak threads
                try {
                    Thread t = new Thread(pair.getFutureTask());
                    t.start();
                    t.join();
                    logger.debug("[Finish] Create time: " + new Date(pair.getTime() * 1000));
                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class PoolCleaner extends Thread {
        public void run() {
            Pair pair;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                        }
                    }
                    pair = (Pair) queue.peek();
                    if (System.currentTimeMillis() / 1000L - pair.getTime() > timeout) {
                        queue.poll();
                        logger.debug("[Drop] Create time: " + new Date(pair.getTime() * 1000));
                        pair.getFutureTask().cancel(true);
                    }
                }
            }
        }
    }

    private class Pair {
        private FutureTask<Object> futureTask;
        private Long time;

        Pair(FutureTask<Object> futureTask, Long time) {
            this.futureTask = futureTask;
            this.time = time;
        }

        public FutureTask<Object> getFutureTask() {
            return futureTask;
        }

        public void setFutureTask(FutureTask<Object> futureTask) {
            this.futureTask = futureTask;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }
    }
}
