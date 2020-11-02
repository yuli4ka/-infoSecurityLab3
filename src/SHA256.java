import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class SHA256 {

    //    первые 32 бита дробных частей квадратных корней
//    первых восьми простых чисел [от 2 до 19]
    private static final int[] H0 = {
            0x6A09E667, 0xBB67AE85, 0x3C6EF372, 0xA54FF53A,
            0x510E527F, 0x9B05688C, 0x1F83D9AB, 0x5BE0CD19
    };

    //    первые 32 бита дробных частей кубических корней
//    первых 64 простых чисел [от 2 до 311]
    private static final int[] K = {
            0x428A2F98, 0x71374491, 0xB5C0FBCF, 0xE9B5DBA5,
            0x3956C25B, 0x59F111F1, 0x923F82A4, 0xAB1C5ED5,
            0xD807AA98, 0x12835B01, 0x243185BE, 0x550C7DC3,
            0x72BE5D74, 0x80DEB1FE, 0x9BDC06A7, 0xC19BF174,
            0xE49B69C1, 0xEFBE4786, 0x0FC19DC6, 0x240CA1CC,
            0x2DE92C6F, 0x4A7484AA, 0x5CB0A9DC, 0x76F988DA,
            0x983E5152, 0xA831C66D, 0xB00327C8, 0xBF597FC7,
            0xC6E00BF3, 0xD5A79147, 0x06CA6351, 0x14292967,
            0x27B70A85, 0x2E1B2138, 0x4D2C6DFC, 0x53380D13,
            0x650A7354, 0x766A0ABB, 0x81C2C92E, 0x92722C85,
            0xA2BFE8A1, 0xA81A664B, 0xC24B8B70, 0xC76C51A3,
            0xD192E819, 0xD6990624, 0xF40E3585, 0x106AA070,
            0x19A4C116, 0x1E376C08, 0x2748774C, 0x34B0BCB5,
            0x391C0CB3, 0x4ED8AA4A, 0x5B9CCA4F, 0x682E6FF3,
            0x748F82EE, 0x78A5636F, 0x84C87814, 0x8CC70208,
            0x90BEFFFA, 0xA4506CEB, 0xBEF9A3F7, 0xC67178F2
    };

    private static final int BLOCK_BITS = 512;
    private static final int BLOCK_BYTES = BLOCK_BITS / 8;

    private static final int[] W = new int[64];
    private static int[] h = new int[8];
    private static final int[] TEMP = new int[8];

    public static byte[] hash(byte[] message) {
        h = H0;

    }

    private static int[] padMes(byte[] message) {
        int newBlockLength = message.length % BLOCK_BYTES;
        int blockNum = message.length / BLOCK_BYTES +
                (newBlockLength + 1 + 8 > BLOCK_BYTES ? 2 : 1);

        IntBuffer res = IntBuffer.allocate(blockNum * (BLOCK_BYTES / Integer.BYTES));
        ByteBuffer buf = ByteBuffer.wrap(message);
        for (int i = 0; i < message.length / Integer.BYTES; i++) {
            res.put(buf.getInt());
        }

        ByteBuffer remainder = ByteBuffer.allocate(4);
        remainder.put(buf).put((byte) 0b10000000).rewind(); //set position to 0
        res.put(remainder.getInt());

        res.position(res.capacity() - 2);
        long messageLength = message.length * 8;
        res.put((int) (messageLength >>> 32));
        res.put((int) messageLength);

        return res.array();
    }

}
