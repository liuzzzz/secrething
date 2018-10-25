package com.secrething.learn.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liuzz on 2018/10/10 4:54 PM.
 */
public class LogToCSV {
    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static void main(String[] args) throws Exception {
        //buildCommand(format.parse("2018-10-08"),format.parse("2018-10-16"));
        log2CVS();
    }
    static void buildCommand(Date start,Date end){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        if (end.compareTo(start) >= 0){
            StringBuilder sbff = new StringBuilder();
            for (Date d = calendar.getTime();d.compareTo(end) <=0;d = calendar.getTime()){
                    sbff.append("cat /opt/scf/log/wxrobot/wxrobot.log."+ format.format(d) +".log | grep 'Group Chat' >> chat_$1.log\n");

                calendar.add(Calendar.DAY_OF_YEAR,1);
            }
            sbff.append("cat /opt/scf/log/wxrobot/wxrobot.log | grep 'Group Chat' >> chat_$1.log\n");
            System.out.println(sbff.toString());
        }

    }
    static void log2CVS() throws Exception{
        File file = new File("/Users/liuzz58/Desktop/logs/char.log");
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
