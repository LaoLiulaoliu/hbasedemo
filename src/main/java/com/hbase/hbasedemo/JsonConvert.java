package com.hbase.hbasedemo;

import com.hbase.hbasedemo.structure.*;
import com.google.gson.stream.JsonReader;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;


public class JsonConvert {

    public String[] names = {
            "lastLogon",
            "acctSummary",
            "sampleLog"
    };

    public void readJson(String fileName) {
        String path = this.getClass().getClassLoader().getResource(fileName).getPath();
        File file = new File(path);
        Reader isreader = null;
        lastLogon lg = null;
        LastLogonFlat llg = null;
        String prefixName = fileName.split(".")[0];

        try {
            isreader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            JsonReader reader = new JsonReader(isreader);
            reader.beginObject();

            if (prefixName == names[0]) {
                lg = readLastLogon(reader);
            } else if (prefixName == names[1]) {
                readSummary(reader);
            } else if (prefixName == names[2]) {
                readSampleLog(reader);
            }

            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BeanUtils.copyProperties(lg, llg);
    }

    private lastLogon readLastLogon(JsonReader reader) {
        lastLogon lg = new lastLogon();
        try {
            while (reader.hasNext()) {
                String s = reader.nextName();
                switch (s) {
                    case "msgType":
                        lg.msgType = reader.nextString();
                        break;
                    case "miTime":
                        lg.miTime = reader.nextString();
                        break;
                    case "channel":
                        lg.channel = reader.nextString();
                        break;
                    case "custId":
                        lg.custId = reader.nextString();
                        break;
                    case "event":
                        lg.event = reader.nextString();
                        break;
                    case "data":
                        lg.data = readLogonData(reader);
                        break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lg;
    }

    private logonData readLogonData(JsonReader reader) {
        logonData lgData = new logonData();
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String s = reader.nextName();
                switch (s) {
                    case "camlevel":
                        lgData.camlevel = reader.nextString();
                        break;
                    case "custType":
                        lgData.custType = reader.nextString();
                        break;
                    case "custSeg":
                        lgData.custSeg = reader.nextString();
                        break;
                    case "guid":
                        lgData.guid = reader.nextString();
                        break;
                    case "dateOfBirth":
                        lgData.dateOfBirth = reader.nextString();
                        break;
                    case "jobTitlFull":
                        lgData.jobTitlFull = reader.nextString();
                        break;
                    case "addressDtl":
                        lgData.addressDtl = readAddress(reader);
                        break;
                }

            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lgData;
    }

    public Address readAddress(JsonReader reader) {
        Address ad = new Address();
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String s = reader.nextName();
                switch (s) {
                    case "ctryCde":
                        ad.ctryCde = reader.nextString();
                        break;
                    case "line3":
                        ad.line3 = reader.nextString();
                        break;
                    case "line2":
                        ad.line2 = reader.nextString();
                        break;
                    case "line1":
                        ad.line1 = reader.nextString();
                        break;
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ad;
    }

    private void readSummary(JsonReader reader) {
        try {
            while (reader.hasNext()) {
                String s = reader.nextName();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readSampleLog(JsonReader reader) {
        try {
            while (reader.hasNext()) {
                String s = reader.nextName();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
