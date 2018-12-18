package com.secrething.common.util;

import com.alibaba.fastjson.JSON;
import com.sun.management.GcInfo;
import org.apache.commons.lang3.StringUtils;
import sun.jvm.hotspot.runtime.VM;
import sun.jvm.hotspot.tools.Tool;

import javax.management.Notification;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @author liuzz
 * @create 2018/3/14
 */
public abstract class MesgFormatter {
    private static final String PLACE_HOLDER = "{}";
    static int split = PLACE_HOLDER.length();

    private MesgFormatter() {
        throw new UnsupportedOperationException("");
    }

    /**
     * 字符串拼接,拼接方式如同slf4j日志的输出方式
     * 例: format("h{}ll{}","e","o") return hello
     *
     * @param pattern
     * @param params
     * @return
     */
    public static final String format(String pattern, Object... params) {
        return formatWithHolder(pattern,PLACE_HOLDER,params);
    }

    public static final String formatWithHolder(String pattern, String hoder, Object... params) {
        if (params.length < 1 || StringUtils.isBlank(pattern))
            return pattern;
        char[] src = pattern.toCharArray();
        int offset = 0;
        int idx = pattern.indexOf(hoder, offset);
        if (idx < 0)
            return pattern;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < params.length && idx > -1; i++) {
            int len = idx - offset;
            if (len > 0)
                builder.append(src, offset, len);
            builder.append(params[i]);
            offset = idx + split;
            idx = pattern.indexOf(hoder, offset);
        }
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }

    public static final void println(String pattern, Object... params) {
        System.out.println(format(pattern, params));
    }
    public static final void println(Object params) {
        System.out.println(format("{}", params));
    }
    public static final void print(String pattern, Object... params) {
        System.out.print(format(pattern, params));
    }
    private static class GCListener implements NotificationListener {

        @Override
        public void handleNotification(Notification notification, Object handback) {
            System.out.println(JSON.toJSONString(notification));
            final com.sun.management.GarbageCollectorMXBean gcbean = (com.sun.management.GarbageCollectorMXBean)handback;
            GcInfo gcInfo = gcbean.getLastGcInfo();
            System.out.println(JSON.toJSONString(gcInfo));
        }
    }
    private static class Test extends Tool{

        @Override
        public void run() {
            VM.getVM().registerVMSuspendedObserver((o, arg) -> System.out.println("suspend"));
        }

        @Override
        public void execute(String... args) {
            super.execute(args);
        }
    }
    public static void main(String[] args) throws Exception {
        String pname = ManagementFactory.getRuntimeMXBean().getName();
        new Test().execute(pname.split("@")[0]);
        System.out.println(pname);
        List<GarbageCollectorMXBean> list = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean: list){
            NotificationBroadcaster broadcaster = (NotificationBroadcaster) gcbean;
            broadcaster.addNotificationListener(new GCListener(),null,gcbean);
        }
        System.out.println(System.currentTimeMillis());
        System.gc();
        System.out.println(System.currentTimeMillis());
        Thread.sleep(1000);
        Thread t =  new Thread(() -> {
            try {
                Process process = Runtime.getRuntime().exec(format("jstat -gcutil {} 1000 1000",pname.split("@")[0]));

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                for (String line = reader.readLine();null != line;line = reader.readLine()){
                    System.out.println(line);
                }
            } catch (IOException e) {

            }
        });
        t.setDaemon(true);
        t.start();
        Thread.sleep(10000000);

    }
    /*private static Object invoke(final Object target, final String methodName, final Class<?>... args) {
        return AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                try {
                    Method method = method(target, methodName, args);
                    method.setAccessible(true);
                    return method.invoke(target);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        });
    }
    private static Method method(Object target, String methodName, Class<?>[] args)
            throws NoSuchMethodException {
        try {
            return target.getClass().getMethod(methodName, args);
        } catch (NoSuchMethodException e) {
            return target.getClass().getDeclaredMethod(methodName, args);
        }
    }*/
}
