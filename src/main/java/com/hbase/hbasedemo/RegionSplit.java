package com.hbase.hbasedemo;

import org.apache.hadoop.hbase.util.Bytes;

public class RegionSplit {
    private String[] pointInfos = {
            "0001",
            "0002",
            "0003",
            "0004",
            "0005",
            "0006",
            "0007",
            "0008",
            "0009",
            "0010",
            "0011",
            "0012",
            "0013",
            "0014",
            "0015",
            "0016",
            "0017",
            "0018",
            "0019",
            "0020"
    };

    public byte[][] split() {
        byte[][] result = new byte[pointInfos.length][];
        for (int i = 0; i < pointInfos.length; i++) {
            result[i] = Bytes.toBytes(pointInfos[i]);
        }
        return result;
    }
}
