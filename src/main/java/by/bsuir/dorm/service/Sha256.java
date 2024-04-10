package by.bsuir.dorm.service;

public interface Sha256 {

    String getAlgorithm();

    byte[] hash(byte[] data);

    String toHexString(byte[] data);
}
