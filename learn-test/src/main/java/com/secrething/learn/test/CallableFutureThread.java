package com.secrething.learn.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by liuzz on 2018-12-20 20:06.
 */
public class CallableFutureThread {
    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    public static void main(String[] args) {
        for(int i = 0; i< 100; i ++){
            threadPoolExecutor.submit(new RunnableWork(i));
            //System.out.println(map.get());
        }
        System.out.println("==执行完了==");
    }
}
class CallableWork implements Callable<Map<String, Object>> {
    private int i ;
    public CallableWork(int i){
        this.i = i;
    }
    @Override
    public Map<String, Object> call() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("testKey", "我是Callable"+i);
        return param;
    }

}
class RunnableWork implements Runnable {
    private int i ;
    public RunnableWork(int i){
        this.i = i;
    }
    @Override
    public void run()  {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("testKey", "我是Callable"+i);
    }

}