package com.ican.async;

public enum EventType {
    TEST(0),
    PROJECT_FOLLOW(1),
    PROJECT_WARN(2),
    PROJECT_ADD(3);
/*    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);*/

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
