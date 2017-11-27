package com.hbase.hbasedemo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbase.hbasedemo.structure.*;
import org.apache.commons.beanutils.BeanUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class JsonConvert {

    public LastLogonFlat readLogonJson(String fileName) {

        ObjectMapper objectMapper = new ObjectMapper();
        String path = "/tmp/lastLogon.json";

        lastLogon logon = null;
        LastLogonFlat logonFlat = new LastLogonFlat();

        try {
            logon = objectMapper.readValue(new File(path), lastLogon.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BeanUtils.copyProperties(logonFlat, logon);
            BeanUtils.copyProperties(logonFlat, logon.data);
            BeanUtils.copyProperties(logonFlat, logon.data.addressDtl);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return logonFlat;
    }

    public acctSummary readSummaryJson(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        String path = "/tmp/acctSummary.json";
        acctSummary acct = null;

        try {
            acct = objectMapper.readValue(new File(path), acctSummary.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return acct;
    }

    public List<WechatUser> readSampleLogJson(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

        String path = "/tmp/sampleLog.txt";
        List<WechatUser> wechatUsers = new ArrayList<>();

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File(path)), "UTF-8"));
            String line = null;
            while ((line = input.readLine()) != null) {
                //line = line.replaceAll("\\\\", "");
                //line = line.replaceAll("\"\\{", "{");
                //line = line.replaceAll("\\}\"", "}");
                //sampleLog sample = objectMapper.readValue(line, sampleLog.class);

                WechatUser wechatUser = objectMapper.readValue(line, WechatUser.class);
                //BeanUtils.copyProperties(wechatUser, sample);
                //BeanUtils.copyProperties(wechatUser, sample.data);

                wechatUsers.add(wechatUser);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wechatUsers;
    }
}
