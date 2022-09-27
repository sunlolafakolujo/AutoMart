package com.dapafol.userregistration.staticdata;

public enum BodyType {
    CAR("CAR"),
    TRUCK("TRUCK"),
    TRAILER("TRAILER"),
    VAN("VAN");

    private final String bodyType;

    BodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getBodyType() {
        return bodyType;
    }

    @Override
    public String toString() {
        return "BodyType{" +
                "bodyType='" + bodyType + '\'' +
                '}';
    }
}
