package Question3;

public class LogProcessorTask implements Runnable {
    private String logEntry;

    public LogProcessorTask(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public void run() {
        System.out.println("[LOG PROCESSOR] " + Thread.currentThread().getName() + " processing: " + logEntry);
        try {
            Thread.sleep(150); // Simulate I/O processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Log processing interrupted: " + e.getMessage());
        }
    }
}