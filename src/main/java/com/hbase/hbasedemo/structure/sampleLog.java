package com.hbase.hbasedemo.structure;

public class sampleLog {
    public String msgType;
    public String miTime;
    public String channel;
    public String openId;
    public String event;
    public cardData data;

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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public cardData getData() {
        return data;
    }

    public void setData(cardData data) {
        this.data = data;
    }

}
