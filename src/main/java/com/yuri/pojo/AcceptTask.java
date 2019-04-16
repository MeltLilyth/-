package com.yuri.pojo;

public class AcceptTask {
    private String userId;
    private ServiceMessage serviceMessage;
    private int taskNum;
    private int delayTime;

    public AcceptTask(String userId, ServiceMessage serviceMessage, int taskNum) {
        this.userId = userId;
        this.serviceMessage = serviceMessage;
        this.taskNum = taskNum;
        if(this.serviceMessage!=null){
            if(serviceMessage.getFinishDate().getTime()-serviceMessage.getServiceDate().getTime()>=4*24*60*60*1000){
                this.delayTime=1;
            }
            else{
                this.delayTime=0;
            }
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ServiceMessage getServiceMessage() {
        return serviceMessage;
    }

    public void setServiceMessage(ServiceMessage serviceMessage) {
        this.serviceMessage = serviceMessage;
    }

    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public String toString() {
        return "AcceptTask{" +
                "userId='" + userId + '\'' +
                ", serviceMessage=" + serviceMessage +
                ", taskNum=" + taskNum +
                ", delayTime=" + delayTime +
                '}';
    }
}
