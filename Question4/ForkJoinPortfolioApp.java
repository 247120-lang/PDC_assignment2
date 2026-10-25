package Question4;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPortfolioApp {
    public static void main(String[] args) {
        int datasetSize = 10000;
        double[] portfolio = new double[datasetSize];
        Random rand = new Random(42);

        for (int i = 0; i < datasetSize; i++) {
            portfolio[i] = 50 + (rand.nextDouble() * 950);
        }

        ForkJoinPool pool = new ForkJoinPool();

        long startTime = System.nanoTime();
        ParallelRiskCalculator riskTask = new ParallelRiskCalculator(portfolio, 0, portfolio.length);
        double totalRiskBefore = pool.invoke(riskTask);
        long riskCalcTime = System.nanoTime() - startTime;

        double riskCap = 500.0;
        PortfolioThresholdTask stressTestTask = new PortfolioThresholdTask(portfolio, 0, portfolio.length, riskCap);
        pool.invoke(stressTestTask);

        double totalRiskAfter = pool.invoke(new ParallelRiskCalculator(portfolio, 0, portfolio.length));

        System.out.println("=== Fork/Join Portfolio Assessment ===");
        System.out.printf("Total Risk Score (Before Stress Test): $%.2f%n", totalRiskBefore);
        System.out.printf("Total Risk Score (After 5%% Penalty):  $%.2f%n", totalRiskAfter);
        System.out.printf("Execution Time (Risk Calculation):    %.3f ms%n", riskCalcTime / 1e6);
    }
}
