package com.secrething.learn.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by liuzz on 2018/9/20 1:35 PM.
 */
public class TestBitMap {
    public static int buildBit(String s) {
        int length = s.length();
        int res = 0;
        for (int k = 0;k < length;k++) {
            char c= s.charAt(k);
            int i = (int) c - 64;
            res |= 1 << i;
        }
        return res;
    }

    static int log2(int value){
        int x = 0;
        while (value > 1) {
            value >>= 1;
            x++;
        }
        return x;
    }

    public static String begin(String s1,String s2){
        int bit1 = buildBit(s1);
        int bit2 = buildBit(s2);
        int and = bit1 & bit2;
        if (and == 0){
            return "A";
        }else {
            if ((and & (and -1)) == 0){
                int v = log2(and);
                return "B\n"+((char)v+64);
            }else {
                int size = 0;
                StringBuilder sbff = new StringBuilder();
                for (int i = 1; i <27 ; i++) {
                    if (((1<<i) & and) > 0 ){
                        sbff.append((char)(i+64)).append("-");
                        size++;
                    }

                }
                String s = sbff.toString();
                s = s.substring(0,s.length()-1);

                return "C\n"+size+"\n"+s;
            }
        }

    }
    public static String begin2(String s1,String s2){
        Set<String> set1 = new HashSet<>(Arrays.asList(s1.split("")));
        Set<String> set2 = new HashSet<>(Arrays.asList(s2.split("")));
        set1.retainAll(set2);
        if (set1.size() == 0){
            return "A";
        }else if (set1.size() == 1){
            return "B\n"+set1.iterator().next();
        }else {
            return "C\n"+set1.size()+"\n"+set1.stream().sorted().collect(Collectors.joining("-"));
        }

    }

    public static void main(String[] args) {
        long b2 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            begin("BCKKJJNNBGYIOLLNNGHHJHJJLGKJFGKLJFLGKJSLFJLWEAJFJBKLFJGDQSDSADSADASAWPKOADKDJFKDJSKFJDSFNGNDFJSKKSDJFIWUQOEIJHJNMJHGTFREDXFCBVNBBJHBJNNHJUKKJHHHNNHBVGHNFKSKDFLSJLKJFKSDJLFKJSDLKJFKLSD","BCKKJJNNBGYIOLLNNGHHJHJHJNMJHGTFREDXFCBVNBBJHBJNNHJUKKJHHHNNHBVGHN");
        }
        System.out.println(System.currentTimeMillis() -b2);
        System.out.println(begin("BCKKJJNNBGYIOLLNNGHHJHJJLGKJFGKLJFLGKJSLFJLWEAJFJBKLFJGDQSDSADSADASAWPKOADKDJFKDJSKFJDSFNGNDFJSKKSDJFIWUQOEIJHJNMJHGTFREDXFCBVNBBJHBJNNHJUKKJHHHNNHBVGHNFKSKDFLSJLKJFKSDJLFKJSDLKJFKLSD","BCKKJJNNBGYIOLLNNGHHJHJHJNMJHGTFREDXFCBVNBBJHBJNNHJUKKJHHHNNHBVGHN"));
        long b1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            begin2("BCKKJJNNBGYIOLLNNGHHJHJJLGKJFGKLJFLGKJSLFJLWEAJFJBKLFJGDQSDSADSADASAWPKOADKDJFKDJSKFJDSFNGNDFJSKKSDJFIWUQOEIJHJNMJHGTFREDXFCBVNBBJHBJNNHJUKKJHHHNNHBVGHNFKSKDFLSJLKJFKSDJLFKJSDLKJFKLSD","BCKKJJNNBGYIOLLNNGHHJHJHJNMJHGTFREDXFCBVNBBJHBJNNHJUKKJHHHNNHBVGHN");
        }
        System.out.println(System.currentTimeMillis()-b1);


    }

}
