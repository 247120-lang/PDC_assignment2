package Question3;

public class TelemetryThread extends Thread {
    private boolean running = true;

    public void stopTelemetry() {
        this.running = false;
    }

    @Override
    public void run() {
        int pingCount = 1;
        while (running) {
            System.out.println("[TELEMETRY DAEMON] System Health OK | Ping #" + pingCount++);
            try {
                Thread.sleep(300); // Pulse interval
            } catch (InterruptedException e) {
                System.out.println("[TELEMETRY DAEMON] Interrupted. Shutting down daemon...");
                break;
            }
        }
        System.out.println("[TELEMETRY DAEMON] Daemon thread stopped successfully.");
    }
}