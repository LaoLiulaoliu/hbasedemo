package com.hbase.hbasedemo.structure;

public class prdCatInfo {
    public String entProdTypCde;
    public String entProdCatCde;
    public String ccy;

    public String getEntProdTypCde() {
        return entProdTypCde;
    }

    public void setEntProdTypCde(String entProdTypCde) {
        this.entProdTypCde = entProdTypCde;
    }

    public String getEntProdCatCde() {
        return entProdCatCde;
    }

    public void setEntProdCatCde(String entProdCatCde) {
        this.entProdCatCde = entProdCatCde;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    @Override
    public String toString() {
        return "prdCatInfo{" +
                "entProdTypCde='" + entProdTypCde + '\'' +
                ", entProdCatCde='" + entProdCatCde + '\'' +
                ", ccy='" + ccy + '\'' +
                '}';
    }
}
