package com.hbase.hbasedemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbase.hbasedemo.structure.LastLogonFlat;
import com.hbase.hbasedemo.structure.acctSummary;
import com.hbase.hbasedemo.structure.lastLogon;
import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class JsonConvert {

    public LastLogonFlat readLogonJson(String fileName) {

        ObjectMapper objectMapper = new ObjectMapper();
        String path = this.getClass().getClassLoader().getResource(fileName).getPath();
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

}
