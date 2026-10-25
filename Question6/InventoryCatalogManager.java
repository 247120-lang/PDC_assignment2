package Question6;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InventoryCatalogManager {
    private static final int NUM_THREADS = 10;
    private static final int OPERATIONS_PER_THREAD = 50000;
    private static final int NUM_ITEMS = 100;

    public void benchmarkSynchronizedMap() throws InterruptedException {
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());
        for (int i = 0; i < NUM_ITEMS; i++) {
            map.put("PROD_" + i, 1000);
        }

        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int t = 0; t < NUM_THREADS; t++) {
            executor.submit(() -> {
                for (int i = 0; i < OPERATIONS_PER_THREAD; i++) {
                    String key = "PROD_" + (i % NUM_ITEMS);
                    synchronized (map) {
                        Integer stock = map.get(key);
                        map.put(key, stock + 1);
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long duration = System.currentTimeMillis() - startTime;

        long totalOps = (long) NUM_THREADS * OPERATIONS_PER_THREAD;
        double throughput = (totalOps / (double) duration) * 1000;

        System.out.println("--- Collections.synchronizedMap (Coarse-Grained Locking) ---");
        System.out.println("Time Elapsed: " + duration + " ms");
        System.out.printf("Throughput:   %.2f ops/sec%n%n", throughput);
    }

    public void benchmarkConcurrentMap() throws InterruptedException {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        for (int i = 0; i < NUM_ITEMS; i++) {
            map.put("PROD_" + i, 1000);
        }

        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int t = 0; t < NUM_THREADS; t++) {
            executor.submit(() -> {
                for (int i = 0; i < OPERATIONS_PER_THREAD; i++) {
                    String key = "PROD_" + (i % NUM_ITEMS);
                    map.compute(key, (k, v) -> v == null ? 1 : v + 1);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long duration = System.currentTimeMillis() - startTime;

        long totalOps = (long) NUM_THREADS * OPERATIONS_PER_THREAD;
        double throughput = (totalOps / (double) duration) * 1000;

        System.out.println("--- ConcurrentHashMap (Fine-Grained Bucket Locking) ---");
        System.out.println("Time Elapsed: " + duration + " ms");
        System.out.printf("Throughput:   %.2f ops/sec%n%n", throughput);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Inventory Catalog Concurrency Benchmark ===");
        InventoryCatalogManager manager = new InventoryCatalogManager();
        manager.benchmarkSynchronizedMap();
        manager.benchmarkConcurrentMap();
    }
}
