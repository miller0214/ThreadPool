package homework3;



import java.nio.charset.Charset;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;




public class BloomFilterExample {
    public static void main(String[] args) {
        // 建立布隆過濾器，最大容量 100,000，誤判率 0.01 (1%)
        BloomFilter<String> bloomFilter = BloomFilter.create(
                Funnels.stringFunnel(Charset.defaultCharset()), 100_000, 0.01);


    	
        // 加入數據
        bloomFilter.put("apple");
        bloomFilter.put("banana");
        bloomFilter.put("cherry");

        // 測試數據
        System.out.println("apple 存在嗎？ " + bloomFilter.mightContain("apple"));   // 可能存在
        System.out.println("banana 存在嗎？ " + bloomFilter.mightContain("banana")); // 可能存在
        System.out.println("cherry 存在嗎？ " + bloomFilter.mightContain("cherry")); // 可能存在
        System.out.println("grape 存在嗎？ " + bloomFilter.mightContain("grape"));   // 應該不存在
        System.out.println("orange 存在嗎？ " + bloomFilter.mightContain("orange")); // 應該不存在
    }
}
