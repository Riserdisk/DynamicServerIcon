package com.riserdisk.dynamicservericon.config;

public class RotationConfig {

    /**
     * Interval between icon changes, in seconds.
     */
    private int interval = 10;

    /**
     * Rotation mode.
     * Reserved for future versions.
     */
    private String mode = "SEQUENTIAL";

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

}