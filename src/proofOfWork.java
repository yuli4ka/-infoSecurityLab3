import java.nio.charset.StandardCharsets;

public class proofOfWork {

    public static void main(String[] args) {
        SHA256ProofOfWork("previousTx", "currentTx", "0");
        SHA256ProofOfWork("previousTx", "currentTx", "0000");
    }

    private static void SHA256ProofOfWork(String previousTx, String currentTx, String difficult) {
        long startTime = System.nanoTime();

        int n = 1000;
        int nounce = 0;
        String sha256hex = "";

        for (int i = 0; i < n; i++) {
            nounce = 0;
            while (true) {
                String originalString = previousTx + currentTx + nounce;
                sha256hex = Utils.bytesToHexString(SHA256.hash(originalString.getBytes(StandardCharsets.US_ASCII)));
                if (sha256hex.startsWith(difficult)) {
                    break;
                }
                nounce++;
            }
        }

        long endTime = System.nanoTime();
        System.out.println("Difficulty level: " + difficult + "\nNounce: " +
                nounce + "\nElapsed time: " + ((endTime - startTime) / 1000000.0) / n +
                " milli seconds\nHashedString: " + sha256hex);
        System.out.println("----------------------------------------------------");
    }

}
