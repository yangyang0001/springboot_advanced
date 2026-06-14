package com.deepblue.enums;

public enum SiteIdEnum {


    BP(1, "APPLE","苹果"),
    AP(2, "ANDROID","安卓"),
    GP(3, "NOKIA","诺基亚");

    SiteIdEnum(Integer code, String tenant, String name) {
        this.code = code;
        this.tenant = tenant;
        this.name = name;
    }
    private Integer code;
    private String tenant;
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

}
