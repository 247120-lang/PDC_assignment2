public class PacketSymmetryAuditor {
    public static boolean checkSymmetry(String payload) {
        int length = payload.length();
        TransactionStack stack = new TransactionStack(length);
        RequestQueue queue = new RequestQueue(length);

        for (int i = 0; i < length; i++) {
            char c = payload.charAt(i);
            stack.push(c);
            queue.enqueue(c);
        }

        while (!stack.isEmpty() && !queue.isEmpty()) {
            if (stack.pop() != queue.dequeue()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String testPacket1 = "HEADER-REDACTED-CAT-TAC-REDACTED-REDAEH";
        String testPacket2 = "NETWORK_PACKET_PAYLOAD_12345";

        System.out.println("=== Zero-Trust Packet Symmetry Audit ===");
        System.out.println("Packet 1: " + testPacket1);
        System.out.println("Symmetrical (Palindrome): " + checkSymmetry(testPacket1));

        System.out.println("\nPacket 2: " + testPacket2);
        System.out.println("Symmetrical (Palindrome): " + checkSymmetry(testPacket2));
    }
}