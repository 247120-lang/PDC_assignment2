package Question5;

import java.util.Random;

public class SensorGridMonitorApp {
    public static void main(String[] args) throws InterruptedException {
        int dataSize = 50000;
        double[] telemetryData = new double[dataSize];
        Random rand = new Random(12345);

        for (int i = 0; i < dataSize; i++) {
            telemetryData[i] = -50.0 + (rand.nextDouble() * 200.0);
        }

        int numThreads = 4;
        int chunkSize = dataSize / numThreads;

        ParallelMinSearch[] minTasks = new ParallelMinSearch[numThreads];
        ParallelMaxSearch[] maxTasks = new ParallelMaxSearch[numThreads];
        Thread[] minThreads = new Thread[numThreads];
        Thread[] maxThreads = new Thread[numThreads];

        long startTime = System.nanoTime();

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? dataSize : (i + 1) * chunkSize;

            minTasks[i] = new ParallelMinSearch(telemetryData, start, end);
            maxTasks[i] = new ParallelMaxSearch(telemetryData, start, end);

            minThreads[i] = new Thread(minTasks[i]);
            maxThreads[i] = new Thread(maxTasks[i]);

            minThreads[i].start();
            maxThreads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            minThreads[i].join();
            maxThreads[i].join();
        }

        double globalMin = Double.MAX_VALUE;
        double globalMax = -Double.MAX_VALUE;

        for (int i = 0; i < numThreads; i++) {
            if (minTasks[i].getResult() < globalMin) globalMin = minTasks[i].getResult();
            if (maxTasks[i].getResult() > globalMax) globalMax = maxTasks[i].getResult();
        }

        long executionTime = System.nanoTime() - startTime;

        System.out.println("=== Parallel Sensor Grid Anomaly Monitor ===");
        System.out.printf("Global Minimum Temperature Recorded: %.2f °C%n", globalMin);
        System.out.printf("Global Peak Vibration Spike:        %.2f units%n", globalMax);
        System.out.printf("Parallel Reduction Execution Time:  %.3f ms%n", executionTime / 1e6);
    }
}