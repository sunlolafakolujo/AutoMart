package com.dapafol.userregistration.staticdata;

public enum FlagReason {
    PRICING("PRICING"),
    WEIRD_DEMAND("WEIRD DEMAND");

    private final String flagReason;

    FlagReason(String flagReason) {
        this.flagReason = flagReason;
    }

    public String getFlagReason() {
        return flagReason;
    }

    @Override
    public String toString() {
        return "FlagReason{" +
                "flagReason='" + flagReason + '\'' +
                '}';
    }
}
