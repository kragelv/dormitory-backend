package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.service.Sha256;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class Sha256Impl implements Sha256 {
    private static final String HASH_ALGORITHM = "SHA-256";
    private final MessageDigest md;

    public Sha256Impl() {
        try {
            md = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException("Hash algorithm '" + HASH_ALGORITHM + "' wasn't found", ex);
        }
    }

    @Override
    public String getAlgorithm() {
        return HASH_ALGORITHM;
    }

    @Override
    public byte[] hash(byte[] data) {
        return md.digest(data);
    }

    @Override
    public String toHexString(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }
}
