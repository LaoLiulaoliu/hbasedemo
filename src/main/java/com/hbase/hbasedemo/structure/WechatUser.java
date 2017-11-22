package com.hbase.hbasedemo.structure;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class WechatUser {
    public String msgType;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date miTime;
    public String channel;
    public String openId;
    public String event;

    public String userName;
    public String idType;
    public String idNum;
    public String cardType;
    public String cardNum;
    public String mobileNum;
    public String reasonCode;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    @Override
    public String toString() {
        return "WechatUser{" +
                "msgType='" + msgType + '\'' +
                ", miTime=" + miTime +
                ", channel='" + channel + '\'' +
                ", openId='" + openId + '\'' +
                ", event='" + event + '\'' +
                ", userName='" + userName + '\'' +
                ", idType='" + idType + '\'' +
                ", idNum='" + idNum + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardNum='" + cardNum + '\'' +
                ", mobileNum='" + mobileNum + '\'' +
                ", reasonCode='" + reasonCode + '\'' +
                '}';
    }
}