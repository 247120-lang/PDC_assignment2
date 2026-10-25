package Question3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncWebEngineApp {
    public static void main(String[] args) {
        System.out.println("=== Starting Asynchronous Web Engine Pipeline ===");

        // Start Telemetry Daemon Thread
        TelemetryThread telemetry = new TelemetryThread();
        telemetry.setDaemon(true);
        telemetry.start();

        // Initialize Fixed Thread Pool for Worker Logging Tasks
        ExecutorService executor = Executors.newFixedThreadPool(3);

        String[] inboundLogs = {
            "GET /api/v1/trade - 200 OK",
            "POST /api/v1/order - 201 Created",
            "GET /api/v1/portfolio - 403 Forbidden",
            "PUT /api/v1/settings - 200 OK",
            "DELETE /api/v1/session - 204 No Content"
        };

        for (String log : inboundLogs) {
            executor.submit(new LogProcessorTask(log));
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        telemetry.stopTelemetry();
        System.out.println("=== Web Engine Pipeline Execution Complete ===");
    }
}