package com.secrething.learn.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by liuzz on 2019-03-30 09:37.
 */
public class Match {


    public static void main(String[] args) {
        /*File file = new File("/Users/liuzz58/workspace/secrething/learn-test/src/main/java/com/secrething/learn/algorithm/KMPTest.java");
        matchAnnotation(file);
        System.out.println(String.format("总行数:%d,注释行数:%d",total,notes));*/
        String s = " ";
        matchEmail();
        //System.out.println(' '==s.toCharArray()[0]);
    }

    static void matchIp() {
        String m = "(([0-9]|[1-9]\\d|25[0-5]|2[0-4]\\d|1(\\d{2})).){3}([0-9]|[1-9]\\d|25[0-5]|2[0-4]\\d|1(\\d{2}))";
        for (int i = 0; i < 258; i++) {
            for (int j = 0; j <10 ; j++) {
                for (int k = 0; k < 10; k++) {
                    for (int l = 0; l < 10; l++) {
                        String s = (i+"."+j+"."+k+"."+l);
                        boolean b = (s.matches(m));
                        if (!b){
                            System.out.println(s);
                        }
                    }
                }
            }
        }
    }
    static String begin1 = "\\s*[/][*].*";
    static String end1 = ".*[*][/]\\s*";

    static String begin2 = "\\s*//.*";
    static int notes = 0;
    static int total = 0;
    static void matchAnnotation(File file){

        if (file.isDirectory()){
            File[] files = file.listFiles();
            int len = files.length;
            for (int i = 0; i <len ; i++) {
                matchAnnotation(files[i]);
            }
        }else {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                boolean flag = false;
                String line = null;
                while ((line = bufferedReader.readLine()) != null){
                    ++total;

                    if (line.matches(begin1) && line.matches(end1)){
                        ++notes;
                    }else if (line.matches(begin1)){
                        flag = true;
                        ++notes;
                    }else if (line.matches(begin2)){
                        ++notes;
                    }else if (line.matches(end1)){
                        ++notes;
                        flag = false;
                    }else if (flag){
                        ++notes;
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }



    }


    public static void matchEmail(){
        String rex = "^[a-zA-Z0-9_]{6,16}[@][a-zA-Z0-9]+[.]com$";
        System.out.println("dswew211@11.com".matches(rex));
    }
}