package com.secrething.learn.test;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;


import static org.junit.Assert.*;

/**
 * Created by Idroton on 2018/10/6 2:28 PM.
 */
public class ByteCodeTestTest {

    @Test
    public  void writeWordFile() {
        ByteArrayInputStream bais = null;
         String htmlStr = "hello";
        OutputStream fos = null;
        File word = new File("/Users/Idroton/Desktop/test.doc");
        try {
            byte b[] = htmlStr.getBytes("UTF-8");
            bais = new ByteArrayInputStream(b);
            POIFSFileSystem poifs = new POIFSFileSystem();
            DirectoryEntry directory = poifs.getRoot();
            DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
            fos = new FileOutputStream(word);
            poifs.writeFilesystem(fos);
            bais.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            if (bais != null)
                try {
                    bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    private static class Product{
        private String name;
        private String unit;
        private String count;
        private String isOut;
    }
    @Test
    public void testWrite() throws Exception {
        String templatePath = "/Users/Idroton/Desktop/template.doc";
        InputStream is = new FileInputStream(templatePath);
        HWPFDocument doc = new HWPFDocument(is);
        Range range = doc.getRange();
        //把range范围内的${reportDate}替换为当前的日期
        range.replaceText("${reportDate}", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        List<Product> list = new ArrayList<>(8);
        for (int i = 0; i <8 ; i++) {
            Product p = new Product();
            p.name = "山山水水";
            p.unit = "件";
            p.count = "1";
            p.isOut = "是";
            list.add(p);
        }
        for (int i = 0; i < list.size(); i++) {
            range.replaceText("${pname"+i+"}", list.get(i).name);
            range.replaceText("${unit"+i+"}", list.get(i).unit);
            range.replaceText("${count"+i+"}", list.get(i).count);
            range.replaceText("${isOut"+i+"}", list.get(i).isOut);
        }
        OutputStream os = new FileOutputStream("/Users/Idroton/Desktop/test1.doc");
        //把doc输出到输出流中
        doc.write(os);
        this.closeStream(os);
        this.closeStream(is);
    }

    private void closeStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeStream(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}