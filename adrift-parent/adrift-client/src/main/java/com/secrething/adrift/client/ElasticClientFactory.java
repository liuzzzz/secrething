package com.secrething.adrift.client;


import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.secrething.common.core.Record;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;


public class ElasticClientFactory {

    private static Logger log = LoggerFactory.getLogger(ElasticClientFactory.class);

    private static String esUrl;
    private static String clusterName;
    private static String alias;
    private static String type;
    private static RestHighLevelClient client = ElasticClientHolder.clientInstance;
    private ElasticClientFactory() {
    }

    private static RestHighLevelClient initElasClient() {
        RestHighLevelClient client = null;
        try {
            clusterName = PropUtil.getProperty("es.cluster.name");
            esUrl = PropUtil.getProperty("es.url");
            alias = PropUtil.getProperty("es.alias");
            type = PropUtil.getProperty("es.type");

            log.info(String.format("esurl:%s clusterName:%s alias:%s type:%s", esUrl, clusterName, alias, type));
            List<String> result = Splitter.on(",").trimResults().splitToList(esUrl);

            List<HttpHost> transform = Lists.transform(result, input -> {
                String[] ipPort = input.trim().split(":");
                if (ipPort == null || ipPort.length < 2) {
                    return null;
                }
                return new HttpHost(ipPort[0], Integer.parseInt(ipPort[1]));
            });
            client = new RestHighLevelClient(RestClient.builder(transform.toArray(new HttpHost[0])));
            log.info("elas client init success");
        } catch (Exception e) {
            log.error("init elas client is error", e);
        }
        return client;
    }

    private static class ElasticClientHolder {
        private static final RestHighLevelClient clientInstance = initElasClient();
    }

    public static RestHighLevelClient getClient() {
        return ElasticClientHolder.clientInstance;
    }

    public static void closeClient() {
        try {
            RestHighLevelClient client = getClient();
            if (client != null) {
                client.close();
            }
        } catch (IOException e) {
            log.error("elas client close  error", e);
        }
    }

    public static String getAlias() {
        return alias;
    }

    public static String getClusterName() {
        return clusterName;
    }

    public static String getType() {
        return type;
    }

    public static void main(String[] args) throws Exception {
        testIndex();
    }
    public static void testIndex() throws IOException, InterruptedException {
        Message m = new Message();
        m.setName("张三");
        m.setContent("hello 张三");
        Record record = Record.build(m);
        BulkRequest bulkRequest = new BulkRequest();
        IndexRequest req = new IndexRequest();
        req.index(record.getIndex());
        req.type(record.getType());
        req.id(record.getId()).source(record.getSource());
        bulkRequest.add(req);
        Message m1 = new Message();
        m1.setName("李四");
        m1.setContent("hello 李四");
        Record record1 = Record.build(m1);
        IndexRequest req1 = new IndexRequest();
        req1.index(record1.getIndex());
        req1.type(record1.getType());
        req1.id(record1.getId()).source(record1.getSource());
        bulkRequest.add(req1);
        BulkResponse insertBuilder1= client.bulk(bulkRequest);
        System.out.println(insertBuilder1);



    }
}
