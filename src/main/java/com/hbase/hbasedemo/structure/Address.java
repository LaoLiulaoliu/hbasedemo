package com.hbase.hbasedemo.structure;

public class Address {
    public String ctryCde;
    public String line3;
    public String line2;
    public String line1;

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
        return "Address{" +
                "ctryCde='" + ctryCde + '\'' +
                ", line3='" + line3 + '\'' +
                ", line2='" + line2 + '\'' +
                ", line1='" + line1 + '\'' +
                '}';
    }
}