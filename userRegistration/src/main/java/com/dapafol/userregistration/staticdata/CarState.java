package com.dapafol.userregistration.staticdata;

public enum CarState {
    NEW("NEW"),
    USED("USED");

    private final String carState;

    CarState(String carState) {
        this.carState = carState;
    }

    public String getCarState() {
        return carState;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
