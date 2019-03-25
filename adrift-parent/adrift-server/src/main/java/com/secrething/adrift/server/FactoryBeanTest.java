package com.secrething.adrift.server;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * Created by liuzz on 2019-03-20 10:18.
 */
@Component("testWrapper")
public class FactoryBeanTest implements FactoryBean<TestWrapperedByFactoryBean> {

    public FactoryBeanTest(){
    }

    @Override
    public TestWrapperedByFactoryBean getObject() throws Exception {
        return new TestWrapperedByFactoryBean();
    }

    @Override
    public Class<?> getObjectType() {
        return TestWrapperedByFactoryBean.class;
    }
}
