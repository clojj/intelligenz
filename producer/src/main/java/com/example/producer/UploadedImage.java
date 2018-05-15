package com.example.producer;

public class UploadedImage {

    private final String name;
    private final byte[] bytes;

    public UploadedImage(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
