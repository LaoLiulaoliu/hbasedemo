package com.hbase.hbasedemo;

import com.hbase.hbasedemo.HbaseHelper;
import com.hbase.hbasedemo.RegionSplit;
import com.hbase.hbasedemo.structure.lastLogon;
import com.hbase.hbasedemo.JsonConvert;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;


public class HbaseDemo {
    public final static Logger logger = LoggerFactory.getLogger(HbaseDemo.class);

    public static void main(String[] args) throws IOException {
        JsonConvert jc = new JsonConvert();
        jc.readJson("lastLogon.json");


//        Configuration conf = HBaseConfiguration.create();
//        HbaseHelper helper = HbaseHelper.getHelper(conf);
//        createTable(helper);
//        helper.getRegionSize("FANPOINTINFO");
    }

    public static void createTable(HbaseHelper helper) throws IOException {
        RegionSplit rSplit = new RegionSplit();
        byte[][] splitKeys = rSplit.split();

        helper.createTable("FAN12", splitKeys,"INFO");
    }
}
