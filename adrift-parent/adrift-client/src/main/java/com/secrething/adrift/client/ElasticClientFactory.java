package com.secrething.adrift.client;


import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.secrething.esutil.core.MapWriter;
import com.secrething.esutil.core.Record;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
        for (int k = 0; k < 9; k++) {
            File file = new File("/Users/liuzz58/Desktop/logs/msg"+k+".json");
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String json = fileReader.readLine();

            List<Map> list = JSONObject.parseArray(json, Map.class);
            List<Record> records = new ArrayList<>(1000);
            BulkRequest bulkRequest = new BulkRequest();
            for (int i = 0, j = list.size(); i < j; i++) {
                Message message = MapWriter.parse(list.get(i),Message.class);
                Record record = Record.create(message,UUIDBuilder.genUUID());
                IndexRequest req = new IndexRequest();
                req.index(record.getIndex());
                req.type(record.getType());
                req.id(record.getId()).source(record.getSource());
                if (i != 0  && i % 1000 == 0){
                    BulkResponse insertBuilder1 = client.bulk(bulkRequest);
                    bulkRequest = new BulkRequest();
                    bulkRequest.add(req);
                }else {
                    bulkRequest.add(req);
                }
            }
            if (bulkRequest.numberOfActions() > 0)
                client.bulk(bulkRequest);
            System.out.println("Finished:"+k);
        }

        System.exit(1);
    }
}
