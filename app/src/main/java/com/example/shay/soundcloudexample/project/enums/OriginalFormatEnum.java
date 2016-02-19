package com.example.shay.soundcloudexample.project.enums;

public enum OriginalFormatEnum {
    RAW("raw", 0), MP3("mp3", 1), WAV("wav", 2), OTHER_FORMAT("otherFormat", 3);

    private String stringValue;
    private int intValue;

    private OriginalFormatEnum(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}