package homework2;
import java.util.concurrent.*;

public class FibonacciCallable implements Callable<Integer> {
    private final int n;

    public FibonacciCallable(int n) {
        this.n = n;
    }

    @Override
    public Integer call() throws Exception {
        return fibonacci(n);
    }
    
//    這段程式碼的 目的是讓 Callable 執行 fibonacci(n)，並返回計算結果。
//
//    @Override
//
//    這表示我們正在覆寫 Callable<Integer> 介面的 call() 方法。
//    Callable 介面 是 Java 標準佇列（concurrent）中的一部分，用來表示有返回值的執行緒任務。
//    為什麼 call() 需要 throws Exception？
//
//    Callable 介面的 call() 允許拋出異常（Exception），這與 Runnable 不同。
//    這樣做的好處是，如果 Fibonacci 計算時發生錯誤，可以透過 Future.get() 捕獲異常。

    private int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static void main(String[] args) {
        int number = 10; // 計算費氏數列的第 10 項
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // 提交 Callable 任務
        Future<Integer> future = executor.submit(new FibonacciCallable(number));

        try {
            // 取得並印出結果
            System.out.println("費氏數列第 " + number + " 項結果：" + future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // 關閉執行緒池
            executor.shutdown();
        }
    }
    
//    🔹 這裡 future.get() 的作用
//    executor.submit() 會將 FibonacciCallable 這個任務提交給執行緒池，然後立即返回一個 Future<Integer> 物件。
//
//    但這個時候 任務可能還沒執行完。
//    future.get() 會等待 計算結果：
//
//    當計算完成時，get() 會回傳 call() 方法的 返回值（即 Fibonacci 結果）。
//    如果計算還沒完成，get() 會阻塞（等待），直到結果可用。
//    🔹 如果不使用 get() 會怎樣？
//    如果沒有 future.get()，主程式 不會等候執行緒執行完成，而是會直接往下執行，可能導致結果未計算完成就結束程式。
    
    
   
    
}
