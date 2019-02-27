package com.secrething.adrift.client;


import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.secrething.esutil.core.Record;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
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
import java.lang.reflect.Field;
import java.util.ArrayList;
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
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials("xiaoq", "hello_xiaoq08"));
            client = new RestHighLevelClient(RestClient.builder(transform.toArray(new HttpHost[0])).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)));
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

    public static void testIndex() throws Exception {
        File file = new File("/Users/liuzz58/Desktop/q/basic.csv");

        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        String line = null;
        Class<AirQuality> clzz = AirQuality.class;
        Field[] fields = clzz.getDeclaredFields();
        List<AirQuality> list = new ArrayList<>();
        for (int i = 0; (line = fileReader.readLine()) != null; i++) {
            if (i == 0)
                continue;
            String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
            AirQuality quality = new AirQuality();
            for (int j = 0; j <item.length ; j++) {
                fields[j].setAccessible(true);
                try {
                    fields[j].set(quality,Integer.valueOf(item[j]));
                }catch (Exception e){
                    try {
                        fields[j].set(quality,Double.valueOf(item[j]));
                    }catch (Exception e1){
                        try {
                            fields[j].set(quality,item[j]);
                        }catch (Exception eee){

                        }

                    }

                }
            }
            list.add(quality);

        }



        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0, j = list.size(); i < j; i++) {
            AirQuality quality = list.get(i);
            Record record = Record.create(quality, UUIDBuilder.genUUID());
            IndexRequest req = new IndexRequest();
            req.index(record.getIndex());
            req.type(record.getType());
            req.id(record.getId()).source(record.getSource());
            if (i != 0 && i % 1000 == 0) {
                BulkResponse insertBuilder1 = client.bulk(bulkRequest);
                bulkRequest = new BulkRequest();
                bulkRequest.add(req);
            } else {
                bulkRequest.add(req);
            }
        }
        if (bulkRequest.numberOfActions() > 0)
            client.bulk(bulkRequest);
        System.exit(1);
    }
}
