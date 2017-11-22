package com.hbase.hbasedemo.structure;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class sampleLog {
    public String msgType;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date miTime;
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getMiTime() {
        return miTime;
    }

    public void setMiTime(Date miTime) {
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
