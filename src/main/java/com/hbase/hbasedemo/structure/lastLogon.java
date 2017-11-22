package com.hbase.hbasedemo.structure;

public class lastLogon {
    public String msgType;
    public String miTime;
    public String channel;
    public String custId;
    public String event;
    public logonData data;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMiTime() {
        return miTime;
    }

    public void setMiTime(String miTime) {
        this.miTime = miTime;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public logonData getData() {
        return data;
    }

    public void setData(logonData data) {
        this.data = data;
    }

}
