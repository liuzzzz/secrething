package com.secrething.adrift.client;


import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.VersionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

    public static void main(String[] args) throws IOException {
        testIndex();
    }
    public static void testIndex() throws IOException {
        Map<String, Object> upData = Maps.newHashMap();
        upData.put("cvid", Long.parseLong("100982689830924"));
        upData.put("score", 0.000010849709462863433);
        upData.put("sex", 12);
        upData.put("age", 222);
        upData.put("degree", 1);
        upData.put("workyear", 0);
        upData.put("disableendtime", -1);

        IndexRequest req = new IndexRequest();
        req.index("secrething_test");
        req.type("recall");
        req.id("JTeZDWEBbUxacCBFhXmb").source(upData).version(System.currentTimeMillis())
                .versionType(VersionType.EXTERNAL);
        IndexResponse insertBuilder = client.index(req);
        System.out.println(insertBuilder.toString());

        System.out.println(insertBuilder.status());

    }
}
