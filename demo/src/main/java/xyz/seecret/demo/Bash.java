package xyz.seecret.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by liuzz on 2018-12-01 14:42.
 */
@Component
public class Bash implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String,AbstractTest> abstractTest = applicationContext.getBeansOfType(AbstractTest.class);
        abstractTest.forEach((k,v)->{
            v.hello();
        });
    }
}
