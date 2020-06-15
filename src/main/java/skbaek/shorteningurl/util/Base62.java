package skbaek.shorteningurl.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Base62 {

    private static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final int BASE = BASE62.length;

    public static String encodeToLong(long value) {
        StringBuilder sb = new StringBuilder();

        while(value > 0){
            int i = (int) (value % BASE);
            sb.append(BASE62[i]);
            value /= BASE;
        }
        log.info("encode length check : {}", sb.length() > 8);
        return sb.toString();
    }

    public static long decodeToString(String value) {
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


