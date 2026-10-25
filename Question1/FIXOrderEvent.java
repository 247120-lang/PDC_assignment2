public class FIXOrderEvent extends TradeEvent {
    private String clOrdId;

    public FIXOrderEvent(String senderId, long timestamp,
                         double basePrice, String clOrdId) {
        super(senderId, timestamp, basePrice);
        this.clOrdId = clOrdId;
    }

    @Override
    public void dispatch() {
        double transactionFee = basePrice * 0.0015;
        double totalPrice = basePrice + transactionFee;

        System.out.println("FIX Order Event");
        System.out.println("Sender ID: " + senderId);
        System.out.println("Client Order ID: " + clOrdId);
        System.out.println("Base Price: " + basePrice);
        System.out.println("Exchange Fee (0.15%): " + transactionFee);
        System.out.println("Total Purchase Price: " + totalPrice);
    }
}
