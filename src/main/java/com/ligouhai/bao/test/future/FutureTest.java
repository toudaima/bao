package com.ligouhai.bao.test.future;

import com.ligouhai.bao.test.thread.MyThreadFactory;

import java.util.concurrent.*;

/**
 * @author ligouhai
 * @date 2020-03-18 14:41
 * @description future测试
 */
public class FutureTest {

    public static void main(String[] args) throws Exception {
        MyThreadFactory threadFactory = new MyThreadFactory("我的线程");
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(2, 3, 1L,
                TimeUnit.MINUTES, new SynchronousQueue<>(), threadFactory);
        Future future = executorService.submit(() -> {
            try {
                System.out.println("执行异步");
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("异常");
            }
        });
        //这里会等待执行异步完成后再往下执行
        System.out.println(future.get());
        System.out.println("执行主线程");
    }
}
