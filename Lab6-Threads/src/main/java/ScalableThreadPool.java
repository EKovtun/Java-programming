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
        refillThreadsArray(min);
    }

    public void start() {
        if (isStarted) return;
        isStarted = true;
        lowerBound = min;
        count = 0;
        inWork = 0;
        threads.forEach(Thread::start);
    }

    public void joinStop() {
        if (!isStarted) return;
        lowerBound = 0;
        synchronized (tasks) { tasks.notifyAll(); }
        for (Thread thread : threads) try { thread.join(); } catch (InterruptedException ignore) {}
        refillThreadsArray(min);
        isStarted = false;
    }

    public void execute(Runnable task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }
    }

    private void refillThreadsArray(int numberOfThreads) {
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

    private synchronized void incCount() {
        ++count;
        System.out.printf("Threads: %d; In worK: %d%n", count, inWork);
    }

    private synchronized void decCount() {
        --count;
        System.out.printf("Threads: %d; In worK: %d%n", count, inWork);
    }

    private synchronized void incInWork() {
        ++inWork;
        scale();
        System.out.printf("Threads: %d; In worK: %d%n", count, inWork);
    }

    private synchronized void decInWork() {
        --inWork;
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
