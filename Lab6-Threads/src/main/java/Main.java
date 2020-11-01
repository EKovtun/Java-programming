public class Main {
    public static void main(String[] args) {
        //fixedThreadPoolExample();
        scalableThreadPoolExample();
    }

    private static void fixedThreadPoolExample() {
        final int NUMBER_OF_THREADS = 5;
        final int NUMBER_OF_TASKS = 10;

        FixedThreadPool fixedThreadPool = new FixedThreadPool(NUMBER_OF_THREADS);

        for(int i = 1; i < NUMBER_OF_TASKS + 1; ++i) {
            int finalI = i;
            fixedThreadPool.execute(() -> {
                Long time = finalI * 1000L;
                System.out.printf("Task %d sleep %d%n", finalI, time);
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Task %d unsleep before %d%n", finalI, time);
            });
        }

        fixedThreadPool.start();
        fixedThreadPool.joinStop();
    }

    private static void scalableThreadPoolExample() {
        final int MIN_NUMBER_OF_THREADS = 5;
        final int MAX_NUMBER_OF_THREADS = 20;
        final int NUMBER_OF_TASKS_BEFORE_START = 20; // Добавит столько задач перед запуском пула
        final int NUMBER_OF_TASKS_AFTER_START = 10; // Добавит столько задач после запуска пула

        ScalableThreadPool scalableThreadPool = new ScalableThreadPool(MIN_NUMBER_OF_THREADS, MAX_NUMBER_OF_THREADS);

        for(int i = 1; i < NUMBER_OF_TASKS_BEFORE_START + 1; ++i) {
            int finalI = i;
            scalableThreadPool.execute(() -> {
                Long time = finalI * 1000L;
                System.out.printf("Task %d sleep %d%n", finalI, time);
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Task %d unsleep before %d%n", finalI, time);
            });
        }

        scalableThreadPool.start();

        for(int i = NUMBER_OF_TASKS_BEFORE_START + 1; i < NUMBER_OF_TASKS_BEFORE_START + NUMBER_OF_TASKS_AFTER_START + 1; ++i) {
            int finalI = i;
            scalableThreadPool.execute(() -> {
                Long time = finalI * 1000L;
                System.out.printf("Task %d sleep %d%n", finalI, time);
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Task %d unsleep before %d%n", finalI, time);
            });
        }

//        try {
//            Thread.sleep(50000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        for(int i = NUMBER_OF_TASKS_BEFORE_START + 1; i < NUMBER_OF_TASKS_BEFORE_START + NUMBER_OF_TASKS_AFTER_START + 1; ++i) {
//            int finalI = i;
//            scalableThreadPool.execute(() -> {
//                Long time = finalI * 1000L;
//                System.out.printf("Task %d sleep %d%n", finalI, time);
//                try {
//                    Thread.sleep(time);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.printf("Task %d unsleep before %d%n", finalI, time);
//            });
//        }

//        scalableThreadPool.joinStop();
    }
}
