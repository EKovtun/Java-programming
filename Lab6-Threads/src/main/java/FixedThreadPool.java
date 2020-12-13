import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class FixedThreadPool implements ThreadPool {
    private final Queue<Runnable> tasks = new LinkedList<>();
    private final Collection<Thread> threads = new ArrayList<>();
    private volatile boolean stopFlag;
    private boolean isStarted = false;

    public FixedThreadPool(int numberOfThreads) {
        resetPool(numberOfThreads);
    }

    public void start() {
        if (isStarted) return;
        threads.forEach(Thread::start);
        isStarted = true;
    }

    public void joinStop() {
        if (!isStarted) return;
        stopFlag = true;
        int numberOfThread = threads.size();
        synchronized (tasks) {
            tasks.notifyAll();
        }
        for (Thread thread : threads) try {
            thread.join();
        } catch (InterruptedException ignore) {}
        resetPool(numberOfThread);
        isStarted = false;
    }

    public void execute(Runnable task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }
    }

    private void resetPool(int numberOfThreads) {
        stopFlag = false;
        threads.clear();
        for (; numberOfThreads != 0; --numberOfThreads)
            threads.add(new Thread(new Worker()));
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            try {
                Runnable task;
                while (true) {
                    synchronized (tasks) {
                        while (tasks.isEmpty() && !stopFlag)
                            tasks.wait();
                        if (!tasks.isEmpty())
                            task = tasks.poll();
                        else
                            return;
                    }
                    task.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
