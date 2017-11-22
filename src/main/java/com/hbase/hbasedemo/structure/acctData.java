package com.hbase.hbasedemo.structure;

import java.util.List;

public class acctData {
    public List<acctLite> prdCatWrapperList;

    public List<acctLite> getPrdCatWrapperList() {
        return prdCatWrapperList;
    }

    public void setPrdCatWrapperList(List<acctLite> prdCatWrapperList) {
        this.prdCatWrapperList = prdCatWrapperList;
    }

    @Override
    public String toString() {
        return "acctData{" +
                "prdCatWrapperList=" + prdCatWrapperList +
                '}';
    }
}
