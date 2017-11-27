package com.hbase.hbasedemo;

import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ESHelper {

    private String hostName = "hostname";
    private TransportClient client;

    public void init() {
        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", "onesearch")
                    .put("client.transport.sniff", true)
                    .put("discovery.type", "zen")
                    .put("discovery.zen.minimum_master_nodes", 1)
                    .put("discovery.zen.ping_timeout", "500ms")
                    .put("discovery.initial_state_timeout", "500ms")
                    .build();

            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName), 9300));
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

    public void close() {
        client.close();
    }
}
