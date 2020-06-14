package com.example.android_test;

import java.io.Serializable;
import java.sql.Timestamp;

public class DataVO implements Serializable {
    private String startTime;
    private String endTime;
    private String temP;

    public DataVO() {

    }

    public DataVO(String startTime, String endTime, String temP) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.temP = temP;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTemP() {
        return temP;
    }

    public void setTemP(String temP) {
        this.temP = temP;
    }
}
