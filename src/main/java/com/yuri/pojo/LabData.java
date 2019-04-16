package com.yuri.pojo;

public class LabData {
    private String labId;
    private String labName;

    public LabData() {
    }

    public LabData(String labId, String labName) {
        this.labId = labId;
        this.labName = labName;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    @Override
    public String toString() {
        return "LabData{" +
                "labId='" + labId + '\'' +
                ", labName='" + labName + '\'' +
                '}';
    }
}
