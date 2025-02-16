package homework2;

import java.util.concurrent.*;

public class ShutdownNowExample_4 {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        定時執行任務，例如每隔 2 秒執行一次 "Task executed"。
        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        多個獨立的長時間運行任務，例如三個執行緒持續運行 10 秒的迴圈。

        Runnable task = () -> System.out.println("Task executed: " + System.currentTimeMillis());
//        System.currentTimeMillis() 取得目前的時間戳記（毫秒）。
        
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
//        每 2 秒執行一次 task，因此程式會每 2 秒輸出一次 "Task executed: <時間>"。

        // 執行 3 個長時間運行的執行緒
        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
//                    	執行緒開始運行。
//                    	進入 while 迴圈，檢查 isInterrupted() 是否為 false。
//                    	如果 false：
//                    	印出 "執行緒名稱 is running..."。
//                    	Thread.sleep(1000); 讓執行緒暫停 1 秒後繼續執行。
//                    	如果 true（執行緒被 shutdownNow() 強制中斷）：
//                    	Thread.sleep() 會拋出 InterruptedException。
//                    	迴圈終止，執行緒結束。
                        System.out.println(Thread.currentThread().getName() + " is running...");
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrupted!");
                    Thread.currentThread().interrupt(); // 重新設置中斷狀態
                }
            });
        }
        
        // 在 5 秒後強制關閉執行緒池
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            System.out.println("Attempting to shutdown executor service...");
            executorService.shutdownNow();
            scheduler.shutdownNow();
        }, 5, TimeUnit.SECONDS);
    }
}
