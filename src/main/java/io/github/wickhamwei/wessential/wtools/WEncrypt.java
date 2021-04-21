package io.github.wickhamwei.wessential.wtools;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class WEncrypt {
    // https://cloud.tencent.com/developer/article/1128214
    public static String encrypt(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(s.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(s.getBytes(StandardCharsets.UTF_8));
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] bytes) {
        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            ret.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[aByte & 0x0f]);
        }
        return ret.toString();
    }
}
