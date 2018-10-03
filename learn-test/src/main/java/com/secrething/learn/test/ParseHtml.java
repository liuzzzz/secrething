package com.secrething.learn.test;

/**
 * Created by liuzz on 2018/9/29 2:30 PM.
 */
public class ParseHtml {

    public static String parseHtml(String text){
        StringBuilder sbff = new StringBuilder();
        int lineCount = 0;
        String s = text.replaceAll("<br>","\n");
        for (int i = 0; i < s.length(); i++) {
            char c = text.charAt(i);
            if (lineCount == 0){

            }
            if (c == '\n'){
                sbff.append(c);
                continue;
            }
            if (lineCount <= 80){
                sbff.append(c);
                lineCount ++;
            }else {
                if (c == ' ' || c == '\t'){
                    sbff.append("\n");
                }else {
                    sbff.append(c);
                }
                lineCount = 0;

            }

        }
        return sbff.toString();
    }

    public static void main(String[] args) {

    }
}
