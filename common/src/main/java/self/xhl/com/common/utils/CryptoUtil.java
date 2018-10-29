package self.xhl.com.common.utils;

import java.security.MessageDigest;

/**
 * @author bingo
 * @date 2017/3/13
 */

public class CryptoUtil {
    public static String getMD5(String str) {
        return cryptoStrToHexStr("MD5", str);
    }

    public static String getSha1(String str) {
        return cryptoStrToHexStr("SHA1", str);
    }

    public static String getSha256(String str) {
        return cryptoStrToHexStr("SHA-256", str);
    }

    private static String cryptoStrToHexStr(String algorithm, String str) {
        return toHexString(cryptoStrToBytes(algorithm, str));
    }

    private static String toHexString(byte[] bs) {
        StringBuilder hexValue = new StringBuilder();
        for (byte b : bs) {
            int val = ((int) b) & 0xFF;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    private static byte[] cryptoStrToBytes(String algorithm, String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
            return messageDigest.digest();
        } catch (Exception e) {
            return new byte[0];
        }
    }


}
