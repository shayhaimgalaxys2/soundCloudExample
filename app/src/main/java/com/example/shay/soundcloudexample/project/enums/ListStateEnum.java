package com.example.shay.soundcloudexample.project.enums;

public enum ListStateEnum {
    LIST("list", 0), GRID("grid", 1);

    private String stringValue;
    private int intValue;

    private ListStateEnum(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}