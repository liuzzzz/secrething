package com.secrething.learn.test;

import com.secrething.common.util.Assert;
import com.secrething.common.util.Console;

import java.io.File;
import java.io.FileDescriptor;
import java.io.RandomAccessFile;

/**
 * Created by liuzz on 2018/9/18 11:26 AM.
 */
public class RandomRWFileTest {


    public static void main(String[] args) throws Exception {
        File file = new File("/Users/liuzz58/Desktop/work/jiequ.log");
        Assert.isTrue(file.exists());
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        Console.log(randomAccessFile.readLine());
        FileDescriptor descriptor = randomAccessFile.getFD();
        Console.log(descriptor);

    }
}
