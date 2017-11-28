package com.hbase.hbasedemo;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

public class ESHelper {

    private String hostName = "ec2-13-59-255-67.us-east-2.compute.amazonaws.com";
    private TransportClient client;

    public void init() {
        try {
            Settings settings = Settings.builder()
                    //.put("client.transport.sniff", true)
                    //.put("discovery.type", "zen")
                    //.put("discovery.zen.minimum_master_nodes", 1)
                    //.put("discovery.zen.ping_timeout", "500ms")
                    //.put("discovery.initial_state_timeout", "500ms")
                    .put("transport.type","netty3")
                    .put("http.type", "netty3")
                    .build();

            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(hostName), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void defineMapping() {
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        indicesAdminClient.prepareCreate("wechat")
                .setSettings(Settings.builder()
                        .put("index.number_of_shards", 2)
                        .put("index.number_of_replicas", 1))
                .addMapping("user", "{\n" +
                        "  \"properties\": {\n" +
                        "    \"msgType\": {\"type\": \"string\"}\n" +
                        "    \"miTime\": {\"type\": \"string\"}\n" +
                        "    \"channel\": {\"type\": \"string\"}\n" +
                        "    \"openId\": {\"type\": \"string\"}\n" +
                        "    \"event\": {\"type\": \"string\"}\n" +
                        "    \"userName\": {\"type\": \"string\"}\n" +
                        "    \"idType\": {\"type\": \"string\"}\n" +
                        "    \"idNum\": {\"type\": \"string\"}\n" +
                        "    \"cardType\": {\"type\": \"string\"}\n" +
                        "    \"cardNum\": {\"type\": \"string\"}\n" +
                        "    \"mobileNum\": {\"type\": \"string\"}\n" +
                        "    \"reasonCode\": {\"type\": \"string\"}\n" +
                        "    \"cardType\": {\"type\": \"string\"}\n" +
                        "  }\n}", XContentType.JSON)
                .get();
    }

    public void bulkInsert(List<Map<String, Object>> wechatData) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (int index = 0; index < wechatData.size(); index++) {
            bulkRequest.add(client.prepareIndex("wechat",
                    "user",
                    String.valueOf(index))
                    .setSource(wechatData.indexOf(index)));
            if (index % 500 == 499) {
                BulkResponse bulkResponse = bulkRequest.get();
                if (bulkResponse.hasFailures()) {
                    System.out.println(index + " : in insert to es failed.");
                }
            }
        }
        if (wechatData.size() % 500 != 499) {
            BulkResponse bulkResponse = bulkRequest.get();
            if (bulkResponse.hasFailures()) {
                System.out.println("last insert to es failed.");
            }
        }
    }

    public void close() {
        client.close();
    }
}
