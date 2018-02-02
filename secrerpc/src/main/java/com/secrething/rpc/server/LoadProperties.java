package com.secrething.rpc.server;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by liuzengzeng on 2017/12/16.
 * 加载配置
 */
public final class LoadProperties {
    private static final class Inner {
        private static final Properties p = new Properties();

        static {
            InputStream inputStream = LoadProperties.class.getResourceAsStream("/secret.properties");
            if (null != inputStream)
                try {
                    p.load(inputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }

    public static String getConfig(String key) {
        return Inner.p.getProperty(key);
    }
}
