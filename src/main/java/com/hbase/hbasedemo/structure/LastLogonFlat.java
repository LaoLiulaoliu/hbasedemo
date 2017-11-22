package com.hbase.hbasedemo.structure;

public class LastLogonFlat {
    public String msgType;
    public String miTime;
    public String channel;
    public String custId;
    public String event;

    public String camlevel;
    public String custType;
    public String custSeg;
    public String guid;
    public String dateOfBirth;
    public String jobTitlFull;

    public String ctryCde;
    public String line3;
    public String line2;
    public String line1;

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

    public String getCamlevel() {
        return camlevel;
    }

    public void setCamlevel(String camlevel) {
        this.camlevel = camlevel;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getCustSeg() {
        return custSeg;
    }

    public void setCustSeg(String custSeg) {
        this.custSeg = custSeg;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getJobTitlFull() {
        return jobTitlFull;
    }

    public void setJobTitlFull(String jobTitlFull) {
        this.jobTitlFull = jobTitlFull;
    }

    public String getCtryCde() {
        return ctryCde;
    }

    public void setCtryCde(String ctryCde) {
        this.ctryCde = ctryCde;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    @Override
    public String toString() {
        return "LastLogonFlat{" +
                "msgType='" + msgType + '\'' +
                ", miTime='" + miTime + '\'' +
                ", channel='" + channel + '\'' +
                ", custId='" + custId + '\'' +
                ", event='" + event + '\'' +
                ", camlevel='" + camlevel + '\'' +
                ", custType='" + custType + '\'' +
                ", custSeg='" + custSeg + '\'' +
                ", guid='" + guid + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", jobTitlFull='" + jobTitlFull + '\'' +
                ", ctryCde='" + ctryCde + '\'' +
                ", line3='" + line3 + '\'' +
                ", line2='" + line2 + '\'' +
                ", line1='" + line1 + '\'' +
                '}';
    }
}
