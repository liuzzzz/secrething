package com.secrething.learn.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liuzz on 2018/10/10 4:54 PM.
 */
public class LogToCSV {
    public static void main(String[] args) throws Exception {
        File file = new File("/Users/liuzz58/Desktop/logs/chat.log");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sbff = new StringBuilder();
        sbff.append("\"").append("wx_id").append("\",");
        sbff.append("\"").append("friendIds").append("\",");
        sbff.append("\"").append("content").append("\",");
        sbff.append("\"").append("reqTime").append("\"\n");
        for (String line = reader.readLine();null != line;line = reader.readLine()) {
            String time = line.substring(1,15);
            String json = line.split("Group Chat ")[1];
            JSONObject jsonObject = JSONObject.parseObject(json);
            sbff.append("\"").append(jsonObject.getString("wxId")).append("\",");
            List<String> l = new ArrayList<>();
            JSONArray arr = jsonObject.getJSONArray("friendIds");
            for (int i = 0; i < arr.size(); i++) {
                l.add(arr.getString(i));
            }
            String friends = l.stream().collect(Collectors.joining(","));
            sbff.append("\"").append(friends).append("\",");
            sbff.append("\"").append(jsonObject.getString("content")).append("\",");
            sbff.append("\"").append(time).append("\"\n");

        }
        reader.close();
        File out = new File("/Users/liuzz58/Desktop/logs/chat.csv");

        FileWriter writer = new FileWriter(out);
        writer.write(sbff.toString());
        writer.close();
    }
}
