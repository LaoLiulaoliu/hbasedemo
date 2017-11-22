package com.hbase.hbasedemo;

import com.hbase.hbasedemo.structure.LastLogonFlat;
import com.hbase.hbasedemo.structure.WechatUser;
import com.hbase.hbasedemo.structure.acctSummary;
import com.hbase.hbasedemo.structure.sampleLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;

public class HbaseDemo {
    public final static Logger logger = LoggerFactory.getLogger(HbaseDemo.class);

    public static void main(String[] args) throws IOException {
        JsonConvert jc = new JsonConvert();
        LastLogonFlat logonFlat = jc.readLogonJson("lastLogon.json");
        acctSummary acct = jc.readSummaryJson("acctSummary.json");
        List<WechatUser> samples = jc.readSampleLogJson("sampleLog.txt");

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
