package com.jjump.java.data;

public class ResponseDto {
    String message;
    String word;
    String meaning;
    String path;
    @Override
    public String toString() {
        return "[ResLoginData] message=" + message;
    }
    public String getMeaning() { return meaning; }
}
