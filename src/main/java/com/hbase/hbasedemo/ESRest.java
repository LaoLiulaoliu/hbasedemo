package com.hbase.hbasedemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ESRest {
    private RestClient restClient;

    public void init() {
        restClient = RestClient.builder(
                new HttpHost("18.216.157.20", 9200, "http"))
        .build();
    }

    public void bulkCreateDoc(List<Map<String, Object>> wechatData) {
        for (int index = 0; index < wechatData.size(); index++) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonDoc = null;
            try {
                jsonDoc = mapper.writeValueAsString(wechatData.get(index));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            createDoc(String.valueOf(index), jsonDoc);
        }
    }

    public void createDoc(String index, String jsonDoc) {
        String endpoint = "/wechat/user/" + index;
        HttpEntity entity = null;
        try {
            entity = new NStringEntity(jsonDoc, ContentType.APPLICATION_JSON);
            Response response = restClient.performRequest("PUT",
                    endpoint,
                    Collections.emptyMap(),
                    entity);
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void close() {
        try {
            restClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
