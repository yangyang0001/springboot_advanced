package com.deepblue.enums;

import java.util.Arrays;
import java.util.List;

public enum ChannelEnum {

    SMS("1", "SMS"),
    EMAIL("2", "EMAIL"),
    APP_PUSH("3", "APP_PUSH"),
    ;

    private String code;

    private String name;

    private Integer channelNo;

    ChannelEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public static boolean contains(String code) {
        List<String> codes = Arrays.stream(ChannelEnum.values()).map(item -> item.getCode().trim()).toList();
        return codes.contains(code.trim());
    }

    public static boolean contains(ChannelEnum[] channels, String code) {
        List<String> codes = Arrays.stream(channels).map(item -> item.getCode().trim()).toList();
        return codes.contains(code.trim());
    }
}