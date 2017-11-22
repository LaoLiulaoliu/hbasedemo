package com.hbase.hbasedemo.structure;

public class acctSummary {
    public String msgType;
    public String miTime;
    public String channel;
    public String custId;
    public String event;
    public acctData data;

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

    public acctData getData() {
        return data;
    }

    public void setData(acctData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "acctSummary{" +
                "msgType='" + msgType + '\'' +
                ", miTime='" + miTime + '\'' +
                ", channel='" + channel + '\'' +
                ", custId='" + custId + '\'' +
                ", event='" + event + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
