package com.hbase.hbasedemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbase.hbasedemo.structure.LastLogonFlat;
import com.hbase.hbasedemo.structure.WechatUser;
import com.hbase.hbasedemo.structure.acctSummary;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HbaseDemo {
    //public final static Logger logger = LoggerFactory.getLogger(HbaseDemo.class);

    public static void main(String[] args) throws IOException {
        JsonConvert jc = new JsonConvert();
        LastLogonFlat logonFlat = jc.readLogonJson("lastLogon.json");
        acctSummary acct = jc.readSummaryJson("acctSummary.json");
        List<WechatUser> samples = jc.readSampleLogJson("sampleLog.txt");

        Configuration conf = HBaseConfiguration.create();
        HbaseHelper helper = HbaseHelper.getHelper(conf);
        createTable(helper);
        helper.getRegionSize("banklogon");

        insertData(logonFlat, acct, samples, helper);

        helper.regScan("bank", "151090\\d{7}-\\w+");
    }

    public static void createTable(HbaseHelper helper) throws IOException {
        RegionSplit rSplit = new RegionSplit();
        byte[][] splitKeys = rSplit.split();

        helper.createTable("bank", splitKeys,"logon", "account");
        helper.createTable("wechat", splitKeys,"user");
        helper.getRegionSize("banklogon");
    }

    public static void insertData(LastLogonFlat logonFlat,
                                  acctSummary acct,
                                  List<WechatUser> samples,
                                  HbaseHelper helper) throws IOException {
        String bankLogonRow = logonFlat.miTime + "-" + logonFlat.custId;
        Map<String, Object> qualifierValues = MyBeanUtils.transBean2Map(logonFlat);
        helper.put("bank", bankLogonRow, "logon", qualifierValues);

        String bankAccountRow = logonFlat.miTime + "-" + logonFlat.custId;
        Map<String, Object> mapQvs = MyBeanUtils.transBean2Map(acct);

        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(acct.data);
        mapQvs.put("data", data);
        helper.put("bank", bankAccountRow, "account", mapQvs);

        for (WechatUser wechatUser : samples) {
            Map<String, Object> qvs = MyBeanUtils.transBean2Map(wechatUser);
            String row = wechatUser.miTime.getTime() + wechatUser.openId;
            helper.put("wechat", row, "user", qvs);
        }
    }

}
