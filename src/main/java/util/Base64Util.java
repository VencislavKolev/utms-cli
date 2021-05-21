package util;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class Base64Util {
    public static String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    public static String decodeFromBase64(String encodedValue) {
        return Arrays.toString(Base64.getDecoder().decode(encodedValue));
    }
}