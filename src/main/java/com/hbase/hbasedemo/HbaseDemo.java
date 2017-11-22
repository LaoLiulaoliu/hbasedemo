package com.hbase.hbasedemo;

import com.hbase.hbasedemo.structure.LastLogonFlat;
import com.hbase.hbasedemo.structure.acctSummary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class HbaseDemo {
    public final static Logger logger = LoggerFactory.getLogger(HbaseDemo.class);

    public static void main(String[] args) throws IOException {
        JsonConvert jc = new JsonConvert();
        LastLogonFlat logonFlat = jc.readLogonJson("lastLogon.json");
        acctSummary acct = jc.readSummaryJson("acctSummary.json");

//        Configuration conf = HBaseConfiguration.create();
//        HbaseHelper helper = HbaseHelper.getHelper(conf);
//        createTable(helper);
//        helper.getRegionSize("FANPOINTINFO");
    }

    public static void createTable(HbaseHelper helper) throws IOException {
        RegionSplit rSplit = new RegionSplit();
        byte[][] splitKeys = rSplit.split();

        helper.createTable("FAN12", splitKeys,"INFO");
        helper.getRegionSize("FAN12");
    }
}
