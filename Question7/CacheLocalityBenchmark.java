package Question7;

public class CacheLocalityBenchmark {
    private static final int SIZE = 2000;
    private static final int ITERATIONS = 10;
    private double[][] matrix = new double[SIZE][SIZE];

    public CacheLocalityBenchmark() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = i + j;
            }
        }
    }

    public double traverseRowMajor() {
        double sum = 0.0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    public double traverseColumnMajor() {
        double sum = 0.0;
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        CacheLocalityBenchmark benchmark = new CacheLocalityBenchmark();

        // Warmup runs
        benchmark.traverseRowMajor();
        benchmark.traverseColumnMajor();

        long totalRowTime = 0;
        long totalColTime = 0;

        for (int k = 0; k < ITERATIONS; k++) {
            long startRow = System.nanoTime();
            benchmark.traverseRowMajor();
            totalRowTime += (System.nanoTime() - startRow);

            long startCol = System.nanoTime();
            benchmark.traverseColumnMajor();
            totalColTime += (System.nanoTime() - startCol);
        }

        double avgRowMs = (totalRowTime / (double) ITERATIONS) / 1e6;
        double avgColMs = (totalColTime / (double) ITERATIONS) / 1e6;
        double speedup = avgColMs / avgRowMs;

        System.out.println("=== Cache Locality & Memory Performance Benchmark ===");
        System.out.printf("Matrix Size:                     %d x %d%n", SIZE, SIZE);
        System.out.printf("Row-Major Traversal Avg Time:    %.3f ms%n", avgRowMs);
        System.out.printf("Column-Major Traversal Avg Time: %.3f ms%n", avgColMs);
        System.out.printf("Cache Hit Speedup Ratio:         %.2fx faster%n", speedup);
    }
}
