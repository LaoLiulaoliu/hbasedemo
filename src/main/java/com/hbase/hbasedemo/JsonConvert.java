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
        String path = this.getClass().getClassLoader().getResource(fileName)
                .getPath().replace("file:", "").replace("!", "");
        
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
        String path = this.getClass().getClassLoader().getResource(fileName).getPath();
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

        String path = this.getClass().getClassLoader().getResource(fileName).getPath();
        List<WechatUser> wechatUsers = new ArrayList<>();

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File(path)), "UTF-8"));
            String line = null;
            while ((line = input.readLine()) != null) {
                line = line.replaceAll("\\\\", "");
                line = line.replaceAll("\"\\{", "{");
                line = line.replaceAll("\\}\"", "}");
                sampleLog sample = objectMapper.readValue(line, sampleLog.class);

                WechatUser wechatUser = new WechatUser();
                BeanUtils.copyProperties(wechatUser, sample);
                BeanUtils.copyProperties(wechatUser, sample.data);

                wechatUsers.add(wechatUser);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return wechatUsers;
    }
}
