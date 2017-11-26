package com.hbase.hbasedemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class HiveTable {
    private static String Driver ="org.apache.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive2://127.0.0.1:10000/default";
    private static String name = "root";
    private static String password = "root";
    private Connection connection;

    private String banklogon = "CREATE EXTERNAL TABLE banklogon(key string, msgType string, miTime string, channel string, custId string, event string, camlevel string, custType string, custSeg string, guid string, dateOfBirth string, jobTitlFull string, ctryCde string, line3 string, line2 string, line1 string) "
            + "STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' "
            + "WITH SERDEPROPERTIES (\"hbase.columns.mapping\" = \":key,logon:msgType,logon:miTime,logon:channel,logon:custId,logon:event,logon:camlevel,logon:custType,logon:custSeg,logon:guid,logon:dateOfBirth,logon:jobTitlFull,logon:ctryCde,logon:line3,logon:line2,logon:line1\") "
            + "TBLPROPERTIES (\"hbase.table.name\" = \"banklogon\", \"hbase.mapred.output.outputtable\" = \"banklogon\");";

    private String bankaccount = "CREATE EXTERNAL TABLE bankaccount(key string, msgType string, miTime string, channel string, custId string, event string, data string) "
            + "STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' "
            + "WITH SERDEPROPERTIES (\"hbase.columns.mapping\" = \":key,account:msgType,account:miTime,account:channel,account:custId,account:event,account:data\") "
            + "TBLPROPERTIES (\"hbase.table.name\" = \"bankaccount\", \"hbase.mapred.output.outputtable\" = \"bankaccount\");";

    private String wechatUser = "CREATE EXTERNAL TABLE wechat(key string, msgType string, miTime string, channel string, openId string, event string, userName string, idType string, idNum string, cardType string, cardNum string, mobileNum string, reasonCode string) "
            + "STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' "
            + "WITH SERDEPROPERTIES (\"hbase.columns.mapping\" = \":key,user:msgType,user:miTime,user:channel,user:openId,user:event,user:userName,user:idType,user:idNum,user:cardType,user:cardNum,user:mobileNum,user:resonCode\") "
            + "TBLPROPERTIES (\"hbase.table.name\" = \"wechat\", \"hbase.mapred.output.outputtable\" = \"wechat\");";

    public void init() {
        try {
            Class.forName(Driver);
            connection = DriverManager.getConnection(url, name, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(banklogon);
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            rs = stmt.executeQuery(bankaccount);
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            rs = stmt.executeQuery(wechatUser);
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
