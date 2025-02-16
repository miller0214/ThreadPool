package homework2;

import java.util.concurrent.*;

public class ScheduledThreadPoolExample_3 {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        Runnable task = new Runnable() {
            private int count = 0;
            private final int maxCount = 5;
            
            @Override
            public void run() {
                if (count < maxCount) {
                    System.out.println("Task executed");
                    count++;
                } else {
                    scheduler.shutdown();
                    System.out.println("Scheduler terminated");
                }
            }
        };
        
        scheduler.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
    }
}
