package com.secrething.learn.test;

import com.secrething.common.util.MesgFormatter;
import com.secrething.common.util.Out;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * Created by liuzz on 2018/10/10 2:46 PM.
 */
public class BarcodeUtil {

    static List<Prefix> prefixes = new ArrayList<>();

    static {
        prefixes.add(new Prefix("8019", 14));
        prefixes.add(new Prefix("6691", 8));
        prefixes.add(new Prefix("3378", 9));
        prefixes.add(new Prefix("7700", 9));
        prefixes.add(new Prefix("3833", 9));
        prefixes.add(new Prefix("3831", 9));
        prefixes.add(new Prefix("3943", 9));
        prefixes.add(new Prefix("7510", 10));
        prefixes.add(new Prefix("5412", 10));
        prefixes.add(new Prefix("3379", 10));
        //0,1,2-6,7-9
    }

    /**
     * 生成文件
     *
     * @param msg
     * @param path
     * @return
     */
    public static File generateFile(String msg, String path) {
        File file = new File(path);
        try {
            generate(msg, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    /**
     * 生成字节
     *
     * @param msg
     * @return
     */
    public static byte[] generate(String msg) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(msg, ous);
        return ous.toByteArray();
    }

    /**
     * 生成到流
     *
     * @param msg
     * @param ous
     */
    public static void generate(String msg, OutputStream ous) {
        if (StringUtils.isEmpty(msg) || ous == null) {
            return;
        }

        Code39Bean bean = new Code39Bean();

        // 精细度
        final int dpi = 150;
        // module宽度
        final double moduleWidth = UnitConv.in2mm(1.0f / dpi);

        // 配置对象
        bean.setModuleWidth(moduleWidth);
        bean.setWideFactor(3);
        bean.doQuietZone(false);

        String format = "image/png";
        try {

            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);

            // 生成条形码
            bean.generateBarcode(canvas, msg);

            // 结束绘制
            canvas.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param files 要拼接的文件列表
     * @param type  1  横向拼接， 2 纵向拼接
     * @Description:图片拼接 （注意：必须两张图片长宽一致哦）
     * @author:liuyc
     * @time:2016年5月27日 下午5:52:24
     */
    public static void mergeImage(String[] files, int type, String targetFile) {
        int len = files.length;
        if (len < 1) {
            throw new RuntimeException("图片数量小于1");
        }
        File[] src = new File[len];
        BufferedImage[] images = new BufferedImage[len];
        int[][] ImageArrays = new int[len][];
        for (int i = 0; i < len; i++) {
            try {
                src[i] = new File(files[i]);
                images[i] = ImageIO.read(src[i]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            ImageArrays[i] = new int[width * height];
            ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
        }
        int newHeight = 0;
        int newWidth = 0;
        for (int i = 0; i < images.length; i++) {
            // 横向
            if (type == 1) {
                newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
                newWidth += images[i].getWidth();
            } else if (type == 2) {// 纵向
                newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
                newHeight += images[i].getHeight() + 50;
            }
        }
        if (type == 1 && newWidth < 1) {
            return;
        }
        if (type == 2 && newHeight < 1) {
            return;
        }

        // 生成新图片
        try {
            BufferedImage ImageNew = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            int height_i = 0;
            int width_i = 0;
            for (int i = 0; i < images.length; i++) {
                if (type == 1) {
                    ImageNew.setRGB(width_i, 0, images[i].getWidth(), newHeight, ImageArrays[i], 0,
                            images[i].getWidth());
                    width_i += images[i].getWidth();
                } else if (type == 2) {
                    ImageNew.setRGB(0, height_i, newWidth, images[i].getHeight(), ImageArrays[i], 0, newWidth);
                    height_i += images[i].getHeight() + 50;
                }
            }
            //输出想要的图片
            ImageIO.write(ImageNew, targetFile.split("\\.")[1], new File(targetFile));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        //createBarcode();
        File csv1 = new File(Desktop+"2.csv");
        List<String> list = new ArrayList<>();
        list.addAll(readCSV(csv1));
        Map<Integer,List<String>> map = new HashMap<>();
        for (String s : list) {
            Integer len = s.length();
            List<String> l = map.get(len);
            if (null == l){
                l = new ArrayList<>();
                map.put(len,l);
            }
            l.add(s);
        }
        for (List<String> value : map.values()) {
            createBarcodeBylist(value);
        }


    }
    static final String User = "liuzz58";
    static final String Desktop = "/Users/"+User+"/Desktop/";
    static final String path = "/Users/"+User+"/Desktop/barcode/barcode{}.png";
    static final String targetFile = "/Users/"+User+"/Desktop/barcode_group/barcode{}.png";
    private static void createBarcode() {
        String[] sarr = new String[50];
        int idx = 0;
        int j = 1;
        int index = (int) randomRange(0, 9);
        for (int i = 1; i < 60001; i++) {
            String fileName = MesgFormatter.format(path, i);
            String code = generatorCode(index);
            generateFile(code, fileName);
            if (idx > 49) {
                idx = 0;
                sarr = new String[50];
                j++;
            }
            sarr[idx] = fileName;
            if (idx == 49) {
                MesgFormatter.println("够50了,merge 一把");
                mergeImage(sarr, 2, MesgFormatter.format(targetFile,j));
                for (int k = 0; k < sarr.length; k++) {
                    File file = new File(sarr[k]);
                    if (file.exists()) {
                        file.delete();
                    }
                }
                index = (int) randomRange(0, 9);
            }else {
                index = randomIndex(index);
            }
            idx++;
            MesgFormatter.println("create index {} barcode{}", index,i);
        }
    }


    private static int randomIndex(int lastIndex) {
        if (lastIndex == 0) {
            return lastIndex;
        } else if (lastIndex == 1) {
            return 1;
        } else if (lastIndex > 1 && lastIndex < 7) {
            return (int) randomRange(2, 6);
        } else {
            return (int) randomRange(7, 9);
        }
    }

    private static long randomRange(long min, long max) {
        return (long) (Math.random() * (max - min + 1) + min);
    }

    private static String generatorCode(int index) {
        Prefix p = prefixes.get(index);
        String prefix = p.prefix;
        int count = p.suffix;
        long l = 1;
        for (int k = 1; k < count; k++) {
            l = l * 10;
        }
        long suffix = randomRange(l + 1, l * 10 - 1);
        return prefix + suffix;
    }

    private static class Prefix {
        String prefix;
        int suffix;

        public Prefix(String prefix, int suffix) {
            this.prefix = prefix;
            this.suffix = suffix;
        }
    }
    static int j = 1;
    private static void createBarcodeBylist(List<String> codes) {
        String[] sarr = new String[50];
        int idx = 0;

        int index = (int) randomRange(0, 9);
        for (int i = 1; i < codes.size()+1; i++) {
            try {
                String code = codes.get(i-1);
                String fileName = MesgFormatter.format(path, code);
                generateFile(code, fileName);
                if (idx > 49) {
                    idx = 0;
                    sarr = new String[50];
                    j++;
                }
                sarr[idx] = fileName;
                if (idx == 49) {
                    MesgFormatter.println("够50了,merge 一把");
                    mergeImage(sarr, 2, MesgFormatter.format(targetFile,j));
                    for (int k = 0; k < sarr.length; k++) {
                        File file = new File(sarr[k]);
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                    index = (int) randomRange(0, 9);
                }else {
                    if (i == codes.size()){
                        String[] little = new String[idx+1];
                        System.arraycopy(sarr,0,little,0,little.length);
                        sarr = little;
                        MesgFormatter.println("不够50,但是没码了,merge 一把");
                        mergeImage(sarr, 2, MesgFormatter.format(targetFile,j));
                        for (int k = 0; k < sarr.length; k++) {
                            File file = new File(sarr[k]);
                            if (file.exists()) {
                                file.delete();
                            }
                        }
                    }
                    index = randomIndex(index);
                }
                idx++;
                MesgFormatter.println("create index {} barcode{}", index,i);
            }catch (Exception e){
                Out.log(Arrays.toString(sarr));
                return;
            }

        }
    }
    private static List<String> readCSV(File file) throws Exception {
        List<String> nums = new ArrayList<>();
        if (file.getName().endsWith(".csv")){
            Reader in = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader("No").parse(in);
            boolean head = true;
            for (CSVRecord record : records) {
               if (head){
                  head = false;
               }else {
                   nums.add(record.get("No"));
               }
            }
            in.close();
        }
        return nums;
    }
}
