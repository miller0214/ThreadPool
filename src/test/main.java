//package test;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicLong;
//
//public class main {
//	 public static void main(String[] args) throws InterruptedException {
//	        // 输出到控制台
//	        System.out.println("Hello, World!");
//	        
////	        runMultiThreadTest();
//	        
//	        
////	       ver1
//	        
//	        int start = 1;
//	        int end = 1000000000;
//
//	        // 不使用多執行緒
//	        long startTime = System.nanoTime();
//	        long resultWithoutThread = sumWithoutThread(start, end);
//	        long endTime = System.nanoTime();
//	        System.out.println("不使用多執行緒的結果: " + resultWithoutThread + ", 花費時間: " + (endTime - startTime) / 1_000_000 + " ms");
//
//	        // 使用多執行緒
//	        startTime = System.nanoTime();
//	        long resultWithThread = sumWithThreads(start, end);
//	        endTime = System.nanoTime();
//	        System.out.println("使用多執行緒的結果: " + resultWithThread + ", 花費時間: " + (endTime - startTime) / 1_000_000 + " ms");
//	        
//	        
//	    }
//	 
//	 
//	 private static void runMultiThreadTest() {
//		 Map<Integer, String> hashMap = new HashMap<>();
//		         
//		         for (int i = 1; i < 10;i++) {
//		             hashMap.put(i, "init value" + i);
//		           
//		         }
//
//		         run(hashMap);
//		         System.out.println("============");
////		         runMultiThreadTest(concurrentMap);
//	 }
//	 
//	 private static void run(Map<Integer, String> map) {
//		 
////		 HashMap map = new HashMap<>();
//		 
//		 Thread writeThread = new Thread(() -> {
//	         for (int i = 1; i < 10;i++) {
//	             String newValue = "update value " + i;
//	             System.out.println(Thread.currentThread().getName() + " update: key " + i + "->" + newValue);
//	             map.put(i, newValue);
//	             try {
//	                 Thread.sleep(100);
//	             } catch (Exception e) {
//	                 e.printStackTrace();
//	             }
//	         }
//	     });
//		 
//		 Thread readThread = new Thread(() -> {
//	         for (int i = 1; i < 10;i++) {
//	             String value = map.get(i);
//	             System.out.println(Thread.currentThread().getName() + " read: key " + i + "->" + value);
//
//	             try {
//	                 Thread.sleep(100);
//	             } catch (Exception e) {
//	                 e.printStackTrace();
//	             }
//	         }
//	     });
//		 
//		 writeThread.start();
//		 readThread.start();
//		 
//	 }
//	 
////	 ver1.0
//	 
//	 // 單線程方法
//	    private static long sumWithoutThread(int start, int end) {
//	        long sum = 0;
//	        for (int i = start; i <= end; i++) {
//	            sum += i;
//	        }
//	        return sum;
//	    }
//
//	    // 使用多執行緒方法
//	    private static long sumWithThreads(int start, int end) throws InterruptedException {
//	        int mid = (start + end) / 2;
//	        AtomicLong sum1 = new AtomicLong(0);
//	        AtomicLong sum2 = new AtomicLong(0);
//
//	        Thread thread1 = new Thread(() -> sum1.set(partialSum(start, mid)));
//	        Thread thread2 = new Thread(() -> sum2.set(partialSum(mid + 1, end)));
//
//	        thread1.start();
//	        thread2.start();
//
//	        thread1.join();
//	        thread2.join();
//
//	        return sum1.get() + sum2.get();
//	    }
//
//	    // 計算部分總和
//	    private static long partialSum(int start, int end) {
//	        long sum = 0;
//	        for (int i = start; i <= end; i++) {
//	            sum += i;
//	        }
//	        return sum;
//	    }
//	 
//
//  
//}
