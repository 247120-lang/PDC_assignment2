public class TradingEngineApp {

    public static void main(String[] args) {

        System.out.println("=== Trading Engine Started ===");

        TradeEvent event1 = new FIXOrderEvent(
                "FIX-Gateway",
                System.currentTimeMillis(),
                100.00,
                "ORD-1001"
        );

        TradeEvent event2 = new ReutersQuoteEvent(
                "Reuters-Gateway",
                System.currentTimeMillis(),
                100.00,
                99.80,
                100.20
        );

        event1.dispatch();

        System.out.println();

        event2.dispatch();

        System.out.println();
        System.out.println("=== Trading Engine Completed ===");
    }
}
