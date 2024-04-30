package by.bsuir.dorm.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomUtil {
    private RandomUtil() { }

    public static SecureRandom getSecureRandom() {
        SecureRandom random;
        try {
            random = SecureRandom.getInstance("NativePRNG");
        } catch (NoSuchAlgorithmException ex) {
            random = new SecureRandom();
        }
        return random;
    }
}
