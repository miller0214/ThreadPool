package test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class main2 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub

		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		
				
		 int maxNumber = 1000000;		
		 int mid = maxNumber / 2; // 將 1~100000 拆成兩部分


	        
	        long startTime = System.nanoTime(); // 記錄開始時間
	        
	        Future<Long> future1 = executor.submit(() -> {
	            long sum = 0;
	            for (int i = 1; i <= mid; i++) {
	                sum += i;
	                System.out.println("現在task1 sum:" + sum);
	            }
	            return sum;
	        });
	        Future<Long> future2 = executor.submit(() -> {
	            long sum = 0;
	            for (int i = mid + 1; i <= maxNumber; i++) {
	                sum += i;
	                System.out.println("現在task2 sum:" + sum);
	            }
	            return sum;
	        });

	      



	        long result = future1.get() + future2.get(); // 合併結果

	        long endTime = System.nanoTime(); // 記錄結束時間
	        long duration = endTime - startTime; // 計算總執行時間 (奈秒)

	        executor.shutdown();
	        
	        
	        
	        
	     // 單執行緒版本
	        long startTimeSingle = System.nanoTime();

	        long resultSingle = 0;
	        for (int i = 1; i <= maxNumber; i++) {
	            resultSingle += i;
	        }

	        long endTimeSingle = System.nanoTime();
	        long durationSingle = endTimeSingle - startTimeSingle;

	        // 結果輸出
	        System.out.println("多執行緒版本總和: " + result);
	        System.out.println("多執行緒運算時間: " + (duration / 1_000_000.0) + " 毫秒");

	        System.out.println("單執行緒版本總和: " + resultSingle);
	        System.out.println("單執行緒運算時間: " + (durationSingle / 1_000_000.0) + " 毫秒");

				
	
		
		
	}

}
