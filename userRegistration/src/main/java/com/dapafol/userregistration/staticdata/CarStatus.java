package com.dapafol.userregistration.staticdata;

public enum CarStatus {
    SOLD("SOLD"),
    AVAILABLE("AVAILABLE");

    private final String carStatus;

    CarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public String getCarStatus() {
        return carStatus;
    }

    @Override
    public String toString() {
        return "CarStatus{" +
                "carStatus='" + carStatus + '\'' +
                '}';
    }
}
