package com.hbase.hbasedemo;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Map;
import java.util.NavigableMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.ClusterStatus;
import org.apache.hadoop.hbase.ServerName;
import org.apache.hadoop.hbase.ServerLoad;
import org.apache.hadoop.hbase.RegionLoad;
import org.apache.hadoop.hbase.HRegionInfo;

import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.MetaScanner;
import org.apache.hadoop.hbase.client.RegionLocator;

public class HbaseHelper implements Closeable {

    private Configuration configuration = null;
    private Connection connection = null;
    private Admin admin = null;

    protected HbaseHelper(Configuration configuration) throws IOException,
            MasterNotRunningException, ZooKeeperConnectionException {
        this.configuration = configuration;
        this.connection = ConnectionFactory.createConnection(configuration);
        this.admin = connection.getAdmin();
    }

    public static HbaseHelper getHelper(Configuration configuration) throws IOException {
        return new HbaseHelper(configuration);
    }

    @Override
    public void close() throws IOException {
        connection.close();
    }

    public Connection getConnection() {
        return connection;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void createNamespace(String namespace) {
        try {
            NamespaceDescriptor nd = NamespaceDescriptor.create(namespace).build();
            admin.createNamespace(nd);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void dropNamespace(String namespace, boolean force) {
        try {
            if (force) {
                TableName[] tableNames = admin.listTableNamesByNamespace(namespace);
                for (TableName name : tableNames) {
                    admin.disableTable(name);
                    admin.deleteTable(name);
                }
            }
        } catch (Exception e) {
            // ignore
        }
        try {
            admin.deleteNamespace(namespace);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public boolean existsTable(String table) throws IOException {
        return admin.tableExists(TableName.valueOf(table));
    }

    private boolean existsTable(TableName table) throws IOException {
        return admin.tableExists(table);
    }

    public void disableTable(String table) throws IOException {
        admin.disableTable(TableName.valueOf(table));
    }

    private void disableTable(TableName table) throws IOException {
        admin.disableTable(table);
    }

    public void dropTable(String table) throws IOException {
        TableName tn = TableName.valueOf(table);
        if (existsTable(tn)) {
            if (admin.isTableEnabled(tn))
                disableTable(tn);
            admin.deleteTable(tn);
        }
    }

    public void splitTable(String tableName, byte[] splitPoint) throws IOException{
        TableName table = TableName.valueOf(tableName);
        admin.split(table, splitPoint);
    }

    public void createTable(String table, String... colfams) throws IOException {
        createTable(table, 1, null, colfams);
    }

    public void createTable(String table, byte[][] splitKeys, String... colfams) throws IOException {
        createTable(table, 1, splitKeys, colfams);
    }

    public void createTable(String table, int maxVersions, String... colfams) throws IOException {
        createTable(table, maxVersions, null, colfams);
    }

    public void createTable(String table, int maxVersions, byte[][] splitKeys, String... colfams)
            throws IOException {
        TableName tableName = TableName.valueOf(table);
        if (admin.tableExists(tableName)) {
            System.out.println("Table: " + table + " already exists, Skip.");
            return;
        }
        HTableDescriptor desc = new HTableDescriptor(TableName.valueOf(table));
        desc.setDurability(Durability.ASYNC_WAL);
        for (String cf : colfams) {
            HColumnDescriptor coldef = new HColumnDescriptor(cf);
            coldef.setCompressionType(Algorithm.SNAPPY);
            coldef.setMaxVersions(maxVersions);
            desc.addFamily(coldef);
        }
        if (splitKeys != null) {
            admin.createTable(desc, splitKeys);
        } else {
            admin.createTable(desc);
        }
    }

    /*
    * @startRow: a number
    * @endRow: a number
    * @pad: pading zero total length
    * @setTimestamp: true of false
    * @random: true or false
    * */
    public void fillTable(TableName table, int startRow, int endRow, int numCols,
                          int pad, boolean setTimestamp, boolean random,
                          String... colfams)
            throws IOException {
        Table tbl = connection.getTable(table);
        Random rnd = new Random();

        for (int row = startRow; row <= endRow; row++) {
            Put put = new Put(Bytes.toBytes("row-" + padNum(row, pad)));

            for (int col = 1; col <= numCols; col++) {
                String colName = "col-" + padNum(col, pad);
                for (String cf : colfams) {
                    String val = "val-" + (random ?
                            Integer.toString(rnd.nextInt(numCols)) :
                            padNum(row, pad) + "." + padNum(col, pad));
                    if (setTimestamp) {
                        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(colName), col,
                                Bytes.toBytes(val));
                    } else {
                        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(colName),
                                Bytes.toBytes(val));
                    }
                }
            }
            tbl.put(put);
        }
        tbl.close();
    }

    public void put(String table, String row, String fam, String qual,
                    String val) throws IOException {
        Table tbl = connection.getTable(TableName.valueOf(table));
        Put put = new Put(Bytes.toBytes(row));
        put.addColumn(Bytes.toBytes(fam),
                      Bytes.toBytes(qual),
                      Bytes.toBytes(val));
        tbl.put(put);
        tbl.close();
    }

    public void put(String table, String row, String columnFamily,
                    Map<String, Object> qualifierValues) throws IOException {
        Table tbl = connection.getTable(TableName.valueOf(table));
        Put put = new Put(Bytes.toBytes(row));
        for (Map.Entry<String, Object> entry : qualifierValues.entrySet()) {
            Object obj = entry.getValue();
            if (obj == null) {
                System.out.println("value null: " + table + row + columnFamily + ":" + entry.getKey());
                continue;
            }
            put.addColumn(Bytes.toBytes(columnFamily),
                          Bytes.toBytes(entry.getKey()),
                          Bytes.toBytes((String)entry.getValue()));
        }
        put.setDurability(Durability.SKIP_WAL);
        tbl.put(put);
        tbl.close();
    }

    /* Different columnFamilies can't have same qualifiers,
       this function only support one columnFamily actually.
     */
    public void put(String table, String[] rows, String[] fams, String[] quals,
                    String[] vals) throws IOException {
        Table tbl = connection.getTable(TableName.valueOf(table));
        for (String row : rows) {
            Put put = new Put(Bytes.toBytes(row));
            for (String fam : fams) {
                int v = 0;
                for (String qual : quals) {
                    String val = vals[v < vals.length ? v : vals.length - 1];
                    System.out.println("Adding: " + row + " " + fam + " " +
                            qual + " " + val);
                    put.addColumn(Bytes.toBytes(fam),
                                  Bytes.toBytes(qual),
                                  Bytes.toBytes(val));
                    v++;
                }
            }
            put.setDurability(Durability.SKIP_WAL);
            tbl.put(put);
        }
        tbl.close();
    }

    /* Different columnFamilies can't have same qualifiers,
       this function only support one columnFamily actually.
    */
    public void dump(String table, String[] rows, String[] fams, String[] quals)
            throws IOException {
        Table tbl = connection.getTable(TableName.valueOf(table));
        List<Get> gets = new ArrayList<Get>();
        for (String row : rows) {
            Get get = new Get(Bytes.toBytes(row));
            get.setMaxVersions();
            if (fams != null) {
                for (String fam : fams) {
                    for (String qual : quals) {
                        get.addColumn(Bytes.toBytes(fam), Bytes.toBytes(qual));
                    }
                }
            }
            gets.add(get);
        }
        Result[] results = tbl.get(gets);
        for (Result result : results) {
            for (Cell cell : result.rawCells()) {
                System.out.println("Cell: " + cell +
                        ", Value: " + Bytes.toString(cell.getValueArray(),
                        cell.getValueOffset(), cell.getValueLength()));
            }
        }
        tbl.close();
    }

    public void startStopScan(String table, String start, String stop)
            throws IOException {
        Table tbl = connection.getTable(TableName.valueOf(table));
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(start));
        scan.setStopRow(Bytes.toBytes(stop));
        ResultScanner scanner = tbl.getScanner(scan);
        for (Result result : scanner) {
            System.out.println(result);
        }
        scanner.close();
    }

    public void regScan(String table, String reg)
            throws IOException {
        Table tbl = connection.getTable(TableName.valueOf(table));
        Scan scan = new Scan();
        Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL,
                new RegexStringComparator(reg));
        scan.setFilter(filter);
        ResultScanner scanner = tbl.getScanner(scan);
        for (Result result : scanner) {
            System.out.println(result);
        }
        scanner.close();
    }

    public void splitRegion(String regionName, byte[] splitPoint) throws IOException {
        admin.splitRegion(Bytes.toBytes(regionName), splitPoint);
    }

    public void mergerRegions(String regionNameA, String regionNameB) throws IOException {
        admin.mergeRegions(Bytes.toBytes(regionNameA), Bytes.toBytes(regionNameB), true);
    }

    /*
    * regionName: FANPOINTINFO
    * */
    public void getRegionSize(String regionName) throws IOException{
        ClusterStatus status = admin.getClusterStatus();
        Collection<ServerName> snList = status.getServers();
        int totalSize = 0;
        for (ServerName sn : snList) {
            ServerLoad sl = status.getLoad(sn);
            int storeFileSize = sl.getStorefileSizeInMB();// RS大小
            System.out.println(sn.getServerName() + " storeFileSize: "
                    + storeFileSize + "MB");
            Map<byte[], RegionLoad> rlMap = sl.getRegionsLoad();
            for (RegionLoad rl: rlMap.values()) {
                String rName = rl.getNameAsString();
                System.out.println(sn.getServerName() + " region name: " + rName);
                if(rName.substring(0, rName.indexOf(",")).equals(regionName)) {
                    int regionSize = rl.getStorefileSizeMB();
                    totalSize += regionSize;
                    System.out.println(sn.getServerName() + " " +
                            regionSize + "MB");
                }
            }
        }
        System.out.println("总大小=" + totalSize + "MB");
    }

    public void getRegionsInfo(String tableName) throws IOException{
        TableName tablename = TableName.valueOf(Bytes.toBytes(tableName));
        NavigableMap<HRegionInfo, ServerName> regionMap =
                MetaScanner.allTableRegions(connection, tablename);
        RegionLocator regionLoc = connection.getRegionLocator(tablename);
    }

    public String padNum(int num, int pad) {
        String res = Integer.toString(num);
        if (pad > 0) {
            while (res.length() < pad) {
                res = "0" + res;
            }
        }
        return res;
    }
}
