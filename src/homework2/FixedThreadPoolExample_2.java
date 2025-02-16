package homework2;

import java.util.concurrent.*;

public class FixedThreadPoolExample_2 {
    public static void main(String[] args) {
        int taskCount = 100; // 總共 100 個任務
        int threadPoolSize = 10; // 使用 10 條執行緒
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        long startTime = System.currentTimeMillis(); // 記錄開始時間

        try {
            // 創建 100 個任務
            for (int i = 1; i <= taskCount; i++) {
                int taskNumber = i;
                executor.submit(() -> {
                    try {
                        System.out.println("任務 " + taskNumber + " 開始執行，執行緒：" + Thread.currentThread().getName());
                        Thread.sleep(1000); // 模擬計算 1 秒
                        System.out.println("任務 " + taskNumber + " 執行完成");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } finally {
            executor.shutdown(); // 停止接收新任務
//            這行程式碼的作用是：
//
//            停止接收新任務
//            任何 submit() 或 execute() 都會被拒絕。
//            讓執行緒池完成正在執行的任務
//            已經提交的任務仍然會執行完畢。
//            等到所有任務執行完後，執行緒池才會真正關閉
//            shutdown() 不會立刻終止正在執行的任務，它只是讓執行緒池進入關閉狀態。
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS); // 等待所有任務完成
                
//                如果沒有 shutdown()，執行緒池仍然可以接受新任務
//
//                awaitTermination() 只是等待，但如果有新的任務一直提交進來，它永遠不會返回，導致程式卡住。
//                執行緒池不會自動關閉
//
//                ExecutorService 預設不會在任務執行完畢後自動關閉，所以必須手動 shutdown()。
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis(); // 記錄結束時間
        System.out.println("✅ 總計算時間：" + (endTime - startTime) / 1000 + " 秒");
    }
    
    
    
//    executor.shutdown(); // 告訴執行緒池不要再接收新任務
//    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS); // 等待所有任務執行完成
//    這樣可以確保： ✅ 不會接收新任務
//    ✅ 執行中的任務仍然會完成
//    ✅ 所有任務完成後，執行緒池會正常關閉，程式結束
    
}

