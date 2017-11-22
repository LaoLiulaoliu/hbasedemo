package com.hbase.hbasedemo.structure;

import java.util.List;

public class acctLite {
    public List<prdCatInfo> acctLiteWrapper;
    public String prdCatCde;
    public String ccy;

    public List<prdCatInfo> getAcctLiteWrapper() {
        return acctLiteWrapper;
    }

    public void setAcctLiteWrapper(List<prdCatInfo> acctLiteWrapper) {
        this.acctLiteWrapper = acctLiteWrapper;
    }

    public String getPrdCatCde() {
        return prdCatCde;
    }

    public void setPrdCatCde(String prdCatCde) {
        this.prdCatCde = prdCatCde;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    @Override
    public String toString() {
        return "acctLite{" +
                "acctLiteWrapper=" + acctLiteWrapper +
                ", prdCatCde='" + prdCatCde + '\'' +
                ", ccy='" + ccy + '\'' +
                '}';
    }
}
