package com.deepblue.enums;

/**
 * 不同限流器种类
 */
public enum LimitEnum {

    LIMIT_BATCH("LIMIT_BATCH"),
    LIMIT_SINGLE("LIMIT_SINGLE");

    private String name;

    LimitEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}