package com.yuri.pojo;

import java.util.Date;

public class ServiceMessage {

    private String serviceId; //维修账单id
    private String equipId;   //维修设备id
    private String equipName;  //维修设备名称
    private String userId;    //维修人员id
    private String username;  //维修人员姓名
    private String state;     //受理情况
    private Date serviceDate; //受理时间
    private Date finishDate;   //预计结束时间
    private String process;    //进度

    public ServiceMessage() {
    }

    public ServiceMessage(String serviceId, String equipId, String equipName) {
        this.serviceId = serviceId;
        this.equipId = equipId;
        this.equipName = equipName;
    }

    public ServiceMessage(String serviceId, String equipId, String equipName, String userId, String username, String state, Date serviceDate, Date finishDate, String process) {
        this.serviceId = serviceId;
        this.equipId = equipId;
        this.equipName = equipName;
        this.userId = userId;
        this.username = username;
        this.state = state;
        this.serviceDate = serviceDate;
        this.finishDate = finishDate;
        this.process = process;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    @Override
    public String toString() {
        return "ServiceMessage{" +
                "serviceId='" + serviceId + '\'' +
                ", equipId='" + equipId + '\'' +
                ", equipName='" + equipName + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", state='" + state + '\'' +
                ", serviceDate=" + serviceDate +
                ", finishDate=" + finishDate +
                ", process='" + process + '\'' +
                '}';
    }
}
