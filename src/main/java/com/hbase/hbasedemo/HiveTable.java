package com.hbase.hbasedemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class HiveTable {
    public static final char UNDERLINE = '_';

    private static String Driver ="org.apache.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive2://127.0.0.1:10000/default";
    private static String name = "root";
    private static String password = "root";
    private Connection connection;

    private String banklogon = "CREATE EXTERNAL TABLE IF NOT EXISTS banklogon(key string, msgType string, miTime string, channel string, custId string, event string, camlevel string, custType string, custSeg string, guid string, dateOfBirth string, jobTitlFull string, ctryCde string, line3 string, line2 string, line1 string) "
            + "STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' "
            + "WITH SERDEPROPERTIES (\"hbase.columns.mapping\" = \":key,logon:msgType,logon:miTime,logon:channel,logon:custId,logon:event,logon:camlevel,logon:custType,logon:custSeg,logon:guid,logon:dateOfBirth,logon:jobTitlFull,logon:ctryCde,logon:line3,logon:line2,logon:line1\") "
            + "TBLPROPERTIES (\"hbase.table.name\" = \"banklogon\", \"hbase.mapred.output.outputtable\" = \"banklogon\");";

    private String bankaccount = "CREATE EXTERNAL TABLE IF NOT EXISTS bankaccount(key string, msgType string, miTime string, channel string, custId string, event string, data string) "
            + "STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' "
            + "WITH SERDEPROPERTIES (\"hbase.columns.mapping\" = \":key,account:msgType,account:miTime,account:channel,account:custId,account:event,account:data\") "
            + "TBLPROPERTIES (\"hbase.table.name\" = \"bankaccount\", \"hbase.mapred.output.outputtable\" = \"bankaccount\");";

    private String wechatUser = "CREATE EXTERNAL TABLE IF NOT EXISTS wechat(key string, msgType string, miTime string, channel string, openId string, event string, userName string, idType string, idNum string, cardType string, cardNum string, mobileNum string, reasonCode string) "
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

    public void getWechat() {
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("select count(*) from wechat;");
            while (rs.next()) {
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
            }
            rs = stmt.executeQuery("select a.msgType, a.miTime, a.channel, " +
                    "a.openId, a.event, a.userName, a.idType, a.idNum, " +
                    "a.cardType, a.cardNum, a.mobileNum, a.reasonCode " +
                    "from wechat a;");
            while (rs.next()) {
                System.out.println(rs.getString("reasonCode"));
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String underlineToCamel(String param) {
        if (param == null || param.isEmpty()) {
            return null;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public Object handler(Class clazz, ResultSet rs) {
        try {
            Object bean = clazz.newInstance();
            ResultSetMetaData meta = rs.getMetaData();
            int count = meta.getColumnCount();
            for (int i = 0; i < count; i++) {
                String columnName = meta.getColumnName(i + 1);
                String name = columnName;

            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
