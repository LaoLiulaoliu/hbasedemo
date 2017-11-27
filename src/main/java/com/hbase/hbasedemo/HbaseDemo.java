package com.hbase.hbasedemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbase.hbasedemo.structure.LastLogonFlat;
import com.hbase.hbasedemo.structure.WechatUser;
import com.hbase.hbasedemo.structure.acctSummary;
import org.apache.commons.cli.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HbaseDemo {
    //public final static Logger logger = LoggerFactory.getLogger(HbaseDemo.class);

    public static void main(String[] args) throws IOException {
        Options options = new Options( );
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = null;
        options.addOption("h", "hivetable", false,
                "create hive external table");

        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if ( commandLine.hasOption('h') ) {
            HiveTable hiveTable = new HiveTable();
            hiveTable.init();
            hiveTable.createTable();
            hiveTable.getWechat();
            hiveTable.close();
            System.exit(0);
        }

        JsonConvert jc = new JsonConvert();
        LastLogonFlat logonFlat = jc.readLogonJson("lastLogon.json");
        acctSummary acct = jc.readSummaryJson("acctSummary.json");
        List<WechatUser> samples = jc.readSampleLogJson("sampleLog.txt");

        Configuration conf = HBaseConfiguration.create();
        HbaseHelper helper = HbaseHelper.getHelper(conf);
        createTable(helper);

        insertData(logonFlat, acct, samples, helper);

        helper.regScan("banklogon", "151090\\d{7}-\\w+");
    }

    public static void createTable(HbaseHelper helper) throws IOException {
        RegionSplit rSplit = new RegionSplit();
        byte[][] splitKeys = rSplit.split();

        helper.createTable("banklogon", splitKeys,"logon");
        helper.createTable("bankaccount", splitKeys,"account");
        helper.createTable("wechat", splitKeys,"user");
        helper.getRegionSize("banklogon,0001,1511427314355.3f3052d62df718b412ed4b97480bd7d8.");
    }

    public static void insertData(LastLogonFlat logonFlat,
                                  acctSummary acct,
                                  List<WechatUser> samples,
                                  HbaseHelper helper) throws IOException {
        String bankLogonRow = logonFlat.miTime + "-" + logonFlat.custId;
        Map<String, Object> qualifierValues = MyBeanUtils.transBean2Map(logonFlat);
        helper.put("banklogon", bankLogonRow, "logon", qualifierValues);

        String bankAccountRow = logonFlat.custId + "-" + logonFlat.miTime;
        Map<String, Object> mapQvs = MyBeanUtils.transBean2Map(acct);
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(acct.data);
        mapQvs.put("data", data);
        helper.put("bankaccount", bankAccountRow, "account", mapQvs);

        for (WechatUser wechatUser : samples) {
            Map<String, Object> qvs = MyBeanUtils.transBean2Map(wechatUser);
            String row = wechatUser.miTime.getTime() + '-' + wechatUser.openId;
            System.out.println(wechatUser.miTime.getTime());
            System.out.println(wechatUser.openId);
            System.out.println(row);
            qvs.put("miTime", String.valueOf(wechatUser.miTime.getTime()));
            helper.put("wechat", row, "user", qvs);
        }
    }

}
