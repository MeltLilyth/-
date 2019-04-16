package com.yuri.pojo;
import org.springframework.stereotype.Component;

import java.util.Date;


public class Equipment {
    private String equipId;
    private String equipName;
    private String labName;
    private Date buyDate;
    private Date lastServiceDate;
    private String status;

    public Equipment() {
    }

    public Equipment(String equipId, String equipName, String labName) {
        this.equipId = equipId;
        this.equipName = equipName;
        this.labName = labName;
    }

    public Equipment(String equipId, String equipName, String labName, Date buyDate, Date lastServiceDate) {
        this.equipId = equipId;
        this.equipName = equipName;
        this.labName = labName;
        this.buyDate = buyDate;
        this.lastServiceDate = lastServiceDate;
    }

    public Equipment(String equipId, String equipName, String labName, Date buyDate, Date lastServiceDate, String status) {
        this.equipId = equipId;
        this.equipName = equipName;
        this.labName = labName;
        this.buyDate = buyDate;
        this.lastServiceDate = lastServiceDate;
        this.status = status;
    }

    public String getEquipId() {
        return equipId;
    }

    public void setEquipId(String equipId) {
        this.equipId = equipId;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public Date getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(Date lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "equipId='" + equipId + '\'' +
                ", equipName='" + equipName + '\'' +
                ", labName='" + labName + '\'' +
                ", buyDate=" + buyDate +
                ", lastServiceDate=" + lastServiceDate +
                ", status='" + status + '\'' +
                '}';
    }
}
