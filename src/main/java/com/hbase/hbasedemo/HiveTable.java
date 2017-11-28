package com.hbase.hbasedemo;

import com.hbase.hbasedemo.structure.WechatUser;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Map;

public class HiveTable {
    public static final char UNDERLINE = '_';

    private static String Driver ="org.apache.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive2://127.0.0.1:10000/default";
    private static String name = "";
    private static String password = "";
    private Connection connection;

    private String[] wechatFields = {"msgType", "miTime", "channel", "openId",
            "event", "userName", "idType", "idNum", "cardType", "cardNum",
            "mobileNum", "reasonCode"};

    private String banklogon = "CREATE EXTERNAL TABLE IF NOT EXISTS banklogon(key string, msgType string, miTime string, channel string, custId string, event string, camlevel string, custType string, custSeg string, guid string, dateOfBirth string, jobTitlFull string, ctryCde string, line3 string, line2 string, line1 string) "
            + "STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' "
            + "WITH SERDEPROPERTIES (\"hbase.columns.mapping\" = \":key,logon:msgType,logon:miTime,logon:channel,logon:custId,logon:event,logon:camlevel,logon:custType,logon:custSeg,logon:guid,logon:dateOfBirth,logon:jobTitlFull,logon:ctryCde,logon:line3,logon:line2,logon:line1\") "
            + "TBLPROPERTIES (\"hbase.table.name\" = \"banklogon\", \"hbase.mapred.output.outputtable\" = \"banklogon\")";

    private String bankaccount = "CREATE EXTERNAL TABLE IF NOT EXISTS bankaccount(key string, msgType string, miTime string, channel string, custId string, event string, data string) "
            + "STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' "
            + "WITH SERDEPROPERTIES (\"hbase.columns.mapping\" = \":key,account:msgType,account:miTime,account:channel,account:custId,account:event,account:data\") "
            + "TBLPROPERTIES (\"hbase.table.name\" = \"bankaccount\", \"hbase.mapred.output.outputtable\" = \"bankaccount\")";

    private String wechatUser = "CREATE EXTERNAL TABLE IF NOT EXISTS wechat(key string, msgType string, miTime string, channel string, openId string, event string, userName string, idType string, idNum string, cardType string, cardNum string, mobileNum string, reasonCode string) "
            + "STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' "
            + "WITH SERDEPROPERTIES (\"hbase.columns.mapping\" = \":key,user:msgType,user:miTime,user:channel,user:openId,user:event,user:userName,user:idType,user:idNum,user:cardType,user:cardNum,user:mobileNum,user:reasonCode\") "
            + "TBLPROPERTIES (\"hbase.table.name\" = \"wechat\", \"hbase.mapred.output.outputtable\" = \"wechat\")";

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
            stmt.execute(banklogon);
            stmt.execute(bankaccount);
            stmt.execute(wechatUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getWechat() {
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("select a.msgType, a.miTime, a.channel, " +
                    "a.openId, a.event, a.userName, a.idType, a.idNum, " +
                    "a.cardType, a.cardNum, a.mobileNum, a.reasonCode " +
                    "from wechat a");
            while (rs.next()) {
                try {

                    WechatUser wechatUser = (WechatUser) handler(WechatUser.class, rs);
                    Map<String, Object> qvs = MyBeanUtils.transBean2Map(wechatUser);
                    System.out.println(qvs.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    public Object handler(Class clazz, ResultSet rs) throws Exception {
        Object bean = clazz.newInstance();
        try {
            ResultSetMetaData meta = rs.getMetaData();
            int count = meta.getColumnCount();
            for (int i = 0; i < count; i++) {
                //columnName: a.cardtype
                String name = null;
                String columnName = meta.getColumnName(i + 1);
                Object value = rs.getObject(columnName);
                if (columnName.contains(".")) {
                    String[] split = columnName.split("\\.");
                    if (split.length != 2) {
                        throw  new Exception("incorrect table name！");
                    }
                    name = split[1];
                }

                for (String field : wechatFields) {
                    if (field.toLowerCase().equals(name.toLowerCase())) {
                        name = field;
                        break;
                    }
                }
                System.out.println(columnName + " " + name + " " + value);
                Field f = bean.getClass().getDeclaredField(name);
                if (f != null) {
                    f.setAccessible(true);
                    f.set(bean, value);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
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
