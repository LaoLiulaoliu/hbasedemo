package com.hbase.hbasedemo.structure;

public class logonData {
    public String camlevel;
    public String custType;
    public String custSeg;
    public String guid;
    public String dateOfBirth;
    public String jobTitlFull;
    public Address addressDtl;

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

    public Address getAddressDtl() {
        return addressDtl;
    }

    public void setAddressDtl(Address addressDtl) {
        this.addressDtl = addressDtl;
    }

    @Override
    public String toString() {
        return "logonData{" +
                "camlevel='" + camlevel + '\'' +
                ", custType='" + custType + '\'' +
                ", custSeg='" + custSeg + '\'' +
                ", guid='" + guid + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", jobTitlFull='" + jobTitlFull + '\'' +
                ", addressDtl=" + addressDtl +
                '}';
    }
}