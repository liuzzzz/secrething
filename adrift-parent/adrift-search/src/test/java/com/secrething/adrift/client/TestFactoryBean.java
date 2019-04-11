package com.secrething.adrift.client;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * Created by liuzz on 2018-12-19 18:10.
 */
@Component("hello")
public class TestFactoryBean<T> implements FactoryBean<T> {
    public static class Test{

    }
    @Override
    public T getObject() throws Exception {
        return (T)new Test();
    }

    @Override
    public Class<T> getObjectType() {
        return null;
    }

    private static <T> void concurrentTest(int concurrentThreads, int times, Callable<Long> task, long perTaskTimeout, TimeUnit timeoutUnit) {
        ExecutorService executor = Executors.newFixedThreadPool((int) concurrentThreads);
        long begin = System.currentTimeMillis();
        List<Future<Long>> list = new ArrayList<>();
        List<Callable<Long>> tasks = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            tasks.add(task);
        }
        try {
            list = executor.invokeAll(tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BigDecimal totalCount = BigDecimal.valueOf(list.size());
        BigDecimal successCount = BigDecimal.valueOf(0);
        BigDecimal totalTime = BigDecimal.valueOf(0);
        BigDecimal failCount = BigDecimal.valueOf(0);
        BigDecimal timeoutCount = BigDecimal.valueOf(0);
        BigDecimal successTime = BigDecimal.valueOf(0);
        for (Future<Long> future : list) {
            try {
                BigDecimal b = BigDecimal.valueOf(future.get(perTaskTimeout, timeoutUnit));
                totalTime =totalTime.add(b);
                successTime = successTime.add(b);
                successCount = successCount.add(BigDecimal.valueOf(1));
            } catch (TimeoutException e) {
                //skip
                BigDecimal b = BigDecimal.valueOf(timeoutUnit.toMillis(perTaskTimeout));
                totalTime = totalTime.add(b);
                timeoutCount = timeoutCount.add(BigDecimal.valueOf(1));
            }catch (Exception e){
                failCount = failCount.add(BigDecimal.valueOf(1));
            }
        }
        System.out.println(String.format("提交任务个数={}," +
                        "成功个数={}, \n" +
                        "失败个数={},\n" +
                        "超时个数={},\n" +
                        "总耗时={},\n" +
                        "只成功任务耗时={}\n"+
                        "成功耗时/成功个数={}\n"+
                        "总耗时/成功个数={}\n"+
                        "QPS={}\n"+
                        "总耗时/总个数={}\n",
                totalCount.intValue(),
                successCount.intValue(),
                failCount.intValue(),
                timeoutCount.intValue(),
                totalTime.longValue(),
                successTime.longValue(),
                successTime.divide(successCount,20,ROUND_HALF_UP).doubleValue(),
                totalTime.divide(successCount,20,ROUND_HALF_UP).doubleValue(),
                successCount.divide(totalTime,20,ROUND_HALF_UP).multiply(BigDecimal.valueOf(1000)).multiply(BigDecimal.valueOf(concurrentThreads)).doubleValue(),
                totalTime.divide(totalCount,20,ROUND_HALF_UP).doubleValue()
        ));
        System.out.println();

    }
}
