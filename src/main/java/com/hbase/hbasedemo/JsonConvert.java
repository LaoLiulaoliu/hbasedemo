package com.hbase.hbasedemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbase.hbasedemo.structure.LastLogonFlat;
import com.hbase.hbasedemo.structure.lastLogon;
import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class JsonConvert {

    public void readJson(String fileName) {

        ObjectMapper objectMapper = new ObjectMapper();
        String path = this.getClass().getClassLoader().getResource(fileName).getPath();
        lastLogon logon = null;
        LastLogonFlat logonFlat = null;

        try {
            logon = objectMapper.readValue(new File(path), lastLogon.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BeanUtils.copyProperties(logonFlat, logon);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
