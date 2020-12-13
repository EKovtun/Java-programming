public interface ThreadPool {
    void start();

    /**
     * Остановка пула тредов при окончание выполнения всех задач
     */
    void joinStop();

    void execute(Runnable runnable);
}
