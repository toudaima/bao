package com.ligouhai.bao.test.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ligouhai
 * @date 2020-03-18 15:10
 * @description 自定义线程工厂
 */
public class MyThreadFactory implements ThreadFactory {

    private static final AtomicInteger THREAD_NUMBER = new AtomicInteger(1);

    private String prefix;

    public MyThreadFactory() {}

    public MyThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        System.out.println("创建线程");
        Thread thread = new Thread(r,prefix + THREAD_NUMBER.getAndIncrement());
        return thread;
    }
}
