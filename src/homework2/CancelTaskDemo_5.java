package homework2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class CancellableTask implements Runnable {
    private volatile boolean running = true;

    @Override
    public void run() {
        int count = 0;
        while (running) {
            System.out.println("Count: " + count++);
            try {
                Thread.sleep(500); // 模擬長時間執行
            } catch (InterruptedException e) {
                System.out.println("Task interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Task stopped.");
    }

    public void cancelTask() {
        running = false;
    }
}

public class CancelTaskDemo_5 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CancellableTask task = new CancellableTask();
        Future<?> future = executor.submit(task);
        
        Thread.sleep(3000); // 讓任務運行一段時間
        System.out.println("Cancelling task...");
        task.cancelTask(); // 設置 flag 停止執行
        
//        為什麼這樣設計？
//        優雅停止：不像 Thread.stop() 會強制終止執行緒，這種方式允許執行緒自己決定何時結束，確保程式邏輯能正常清理資源。
//        避免 InterruptedException 依賴：有些情境下，執行緒可能不會立刻被 interrupt() 影響，因此使用 volatile 變數來控制可以確保執行緒正常結束。
//        這樣的設計使 cancelTask() 可以安全、有效地停止 Runnable 執行。
        
        future.cancel(true); // 試圖取消
        
//        task.cancelTask(); 是溫和的方式，讓執行緒主動結束。
//        future.cancel(true); 是較強制的方式，如果任務在 sleep() 或 wait()，它可以立即讓 InterruptedException 發生。
//        一般來說，這兩者可以搭配使用，確保任務能夠安全且迅速地終止。
//        實務上兩個都會一起用嗎
        
        
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        
//        為什麼還需要 shutdown() 和 awaitTermination()？
//        shutdown()：這是告訴執行器不再接受新任務並開始關閉過程的關鍵。
//        它會等待所有已提交的任務完成後再完全停止。如果你不調用 shutdown()，
//        執行器的執行緒會繼續存在，可能會導致資源無法釋放，從而造成內存泄漏等問題。
//
//        awaitTermination()：這確保你的主執行緒會等待執行器完成所有任務後再結束。
//        這樣可以保證所有的執行緒都正確關閉並且釋放資源。
//        如果沒有這個步驟，你的程序可能會在執行緒還在運行時就結束，造成潛在的問題。
        
//        儘管你有 task.cancelTask() 和 future.cancel(true)，
//        這兩者主要是處理單個任務的取消或中斷，
//        但 shutdown() 和 awaitTermination() 是負責關閉整個 ExecutorService 和等待其完全停止的操作。
//        為了確保資源的正確釋放以及所有任務的終止，
//        這兩個方法仍然是必須的。
        
        
        System.out.println("Executor shutdown.");
    }
}

