package com.secrething.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuzz on 2018-11-29 15:45.
 */
public class StoredLog {
    private long fileFromOffset;
    private long fileToOffset;
    private File file;
    private String group;
    private static final String PATH = "/Users/liuzz58/Desktop/MessLog";
    private FileChannel fileChannel;
    private FileOutputStream fos;
    public StoredLog(String group) {
        this.group = group;
        init();
    }
    private void init(){
        File dirs = new File(PATH);

        if (!dirs.exists()){
            dirs.mkdirs();
        }
        this.file = new File(PATH+"/"+group);
        try {
            this.fos = new FileOutputStream(file,true);
            this.fileChannel = new RandomAccessFile(this.file,"rw").getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        t.start();
    }
    ByteBuffer writeBuff = ByteBuffer.allocateDirect(12+1012);
    public synchronized void appendMessage(String mess){
        byte[] msb = mess.getBytes(Charset.forName("UTF-8"));

        writeBuff.putLong(System.currentTimeMillis());
        writeBuff.putInt(msb.length);
        writeBuff.put(msb);
        int len = 12 + msb.length;
        try {


            writeBuff.flip();
            byte[] writes = new byte[len];
            for (int i = 0; i < writeBuff.limit(); i++) {
                writes[i] = writeBuff.get();
            }
            que.offer(writes);
            //int p = fileChannel.write(writeBuff,fileToOffset);
            //MesgFormatter.println("file position:{}",p);
            //MesgFormatter.println("byte array len:{}",12+msb.length);

            //fileChannel.force(false);
            writeBuff.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    Thread t = new Thread(

    ){
        public void run(){
            long b = System.currentTimeMillis();
            while (true){
                try {

                    byte[] writes = que.poll(2, TimeUnit.SECONDS);
                    if (null != writes){
                        fos.write(writes);
                        fileToOffset += writes.length;
                    }else {
                        System.out.println(System.currentTimeMillis()-b);
                        return;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    BlockingQueue<byte[]> que = new LinkedBlockingQueue<>();
    public synchronized String pullMessage(){
        try {

            ByteBuffer buffer = ByteBuffer.allocate(12);
            fileChannel.read(buffer,fileFromOffset);
            fileFromOffset += 12;
            buffer.flip();
            long timestemp = buffer.getLong();
            int msgLen = buffer.getInt();
            buffer = ByteBuffer.allocate(msgLen);

            fileChannel.read(buffer,fileFromOffset);
            fileFromOffset += msgLen;
            String mess = new String(buffer.array(),"UTF-8");
            return mess;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    public synchronized boolean completed(){
        return fileFromOffset >= fileToOffset ;
    }
    public static void main(String[] args) {
        StoredLog storedLog = new StoredLog("1");
        long b1 = System.currentTimeMillis();
        for (int i = 0; i <1000000 ; i++) {
            storedLog.appendMessage("hello"+i);
        }
        long b2 = System.currentTimeMillis();
        MesgFormatter.println("append cost:{}",b2-b1);
        /*int i = 0;
        while (!storedLog.completed()){
            storedLog.pullMessage();
            i++;
        }
        long b3 = System.currentTimeMillis();
        MesgFormatter.println("pull {} cost:{}",i,b3-b2);*/
    }
}
