import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class ScalableThreadPool implements ThreadPool {
    private final Queue<Runnable> tasks = new LinkedList<>();
    private final Collection<Thread> threads = new ArrayList<>();
    private boolean isStarted = false;
    private final int min, max;
    private volatile int lowerBound, count, inWork;

    public ScalableThreadPool(int min, int max) {
        this.min = min;
        this.max = max;
        resetPool(min);
    }

    public void start() {
        if (isStarted) return;
        threads.forEach(Thread::start);
        isStarted = true;
    }

    public void joinStop() {
        if (!isStarted) return;
        lowerBound = 0;
        synchronized (tasks) { tasks.notifyAll(); }
        for (Thread thread : threads) try { thread.join(); } catch (InterruptedException ignore) {}
        resetPool(min);
        isStarted = false;
    }

    public void execute(Runnable task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }
    }

    private void resetPool(int numberOfThreads) {
        lowerBound = min;
        count = 0;
        inWork = 0;
        threads.clear();
        for (; numberOfThreads != 0; --numberOfThreads)
            threads.add(createThread());
    }

    private void scale() {
        if (inWork == count && count < max)
            createThread().start();
    }

    private Thread createThread() {
        return new Thread(new AutoScalableWorker());
    }

    private void incCount() {
        synchronized (tasks) {
            ++count;
        }
        System.out.printf("Threads: %d; In worK: %d%n", count, inWork);
    }

    private void decCount() {
        synchronized (tasks) {
            --count;
        }
        System.out.printf("Threads: %d; In worK: %d%n", count, inWork);
    }

    private void incInWork() {
        synchronized (tasks) {
            ++inWork;
            scale();
        }
        System.out.printf("Threads: %d; In worK: %d%n", count, inWork);
    }

    private void decInWork() {
        synchronized (tasks) {
            --inWork;
        }
        System.out.printf("Threads: %d; In worK: %d%n", count, inWork);
    }

    private class AutoScalableWorker implements Runnable {

        @Override
        public void run() {
            try {
                incCount();
                Runnable task;
                while (true) {
                    synchronized (tasks) {
                        while (tasks.isEmpty() && count <= lowerBound)
                            tasks.wait();
                        task = tasks.poll();
                    }
                    if (task == null) {
                        decCount();
                        return;
                    }
                    executeTask(task);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void executeTask(Runnable task) {
            incInWork();
            task.run();
            decInWork();
        }

    }
}
