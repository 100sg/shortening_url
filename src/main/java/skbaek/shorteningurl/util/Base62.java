package skbaek.shorteningurl.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Base62 {

    /**
     * Base62 Character Table
     */
    private static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final int BASE = BASE62.length;
    /**
     * Base62 Encoding
     *
     * @return the base 62 string of an integer
     */
    public String encode(int value) {
        StringBuilder sb = new StringBuilder();

//        while(value > 0) {
//            sb.append(BASE62[value * BASE]);
//            value /= BASE;
//        }

        do {
            int i = value % BASE;
            sb.append(BASE62[i]);
            value /= BASE;
        } while (value > 0);

        log.info("encode : {}", sb.toString());

        return sb.toString();
    }

    public static String encodeToLong(long value) {
        StringBuilder sb = new StringBuilder();

        while(value > 0){
            int i = (int) (value % BASE);
            sb.append(BASE62[i]);
            value /= BASE;
        }
        log.info("encodeToLong : {}", sb.toString());
        return sb.toString();
    }

    /**
     * Returns the base 62 value of a string.
     *
     * @return the base 62 value of a string.
     */
    public int decode(String value) {
        int result = 0;
        int power = 1;
        for (int i = 0; i < value.length(); i++) {
            int digit = new String(BASE62).indexOf(value.charAt(i));
            result += digit * power;
            power *= BASE;
        }
        log.info("value : {}", result);

        return result;
    }

    public static long decodeToLong(String value) {
        long result = 0;
        long power = 1;
        for (int i = 0; i < value.length(); i++) {
            int digit = new String(BASE62).indexOf(value.charAt(i));
            result += digit * power;
            power *= BASE;
        }
        log.info("decodeToLong : {}", result);
        return result;
    }
}


