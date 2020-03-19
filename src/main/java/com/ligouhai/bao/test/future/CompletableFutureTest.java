package com.ligouhai.bao.test.future;

import com.ligouhai.bao.test.thread.MyThreadFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author ligouhai
 * @date 2020-03-18 16:48
 * @description completableFuture 测试
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws Exception {

        MyThreadFactory threadFactory = new MyThreadFactory("我的线程");
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(2, 3, 1L,
                TimeUnit.MINUTES, new SynchronousQueue<>(), threadFactory);

        //无返回值
        //没有指定Executor的方法会使用ForkJoinPool.commonPool() 作为它的线程池执行异步代码。
        CompletableFuture<Void> futureVoid = CompletableFuture.runAsync(() ->{
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("runAsync无返回值");
        });
        //runAsync需要使用get()方法才会执行
        futureVoid.get();
        System.out.println("执行主线程");

        //有返回值
        CompletableFuture<String> futureString = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync有返回值");
            return "123";
        });

        String s = futureString.get();
        System.out.println("执行结果: " + s);

        //使用 whenComplete
        CompletableFuture whenCompleteFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("正常执行中。。。");
            int i = 1 / 1;
            return "successfully";
        }).whenComplete((r, e) -> {
            //无论是正常执行还是出现异常，都会进入这里, whenComplete和exceptionally这两个 谁在前，谁先执行
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("处理完成");
        }).exceptionally(e -> {
            System.out.println("异常执行中。。。" + e.getMessage());
            return "faild";
        });
        System.out.println("继续执行主线程");

        System.out.println(whenCompleteFuture.get());

        //使用 whenCompleteAsync
        CompletableFuture whenCompleteAsyncFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始正常执行任务2。。。");
            int i = 1 / 1;
            return "成功";
        }).whenCompleteAsync((r, e) -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("处理完成，开始执行");
        }, executorService).exceptionally(e -> {
            System.out.println("出现异常情况，处理失败");
            return "失败";
        });
        System.out.println("处理完成123" + whenCompleteAsyncFuture.isDone());
        System.out.println("处理完成123" + whenCompleteAsyncFuture.get());
        System.out.println("处理完成123" + whenCompleteAsyncFuture.isDone());

        // handle 测试
        CompletableFuture handleFuture = CompletableFuture.supplyAsync(() -> {
            int i = 3;
            System.out.println("handle 正常执行结果 i ：" + i);
            int e = 1 / 0;
            return i;
        }).handle((r, e) -> {
            System.out.println(e.getMessage());
            r += 2;
            System.out.println("handle 执行结果 ： " + r);
            return r;
        }).exceptionally(e -> {
            System.out.println("handle 进入异常执行");
            return 0;
        });
        System.out.println("handle 测试结果 : " + handleFuture.get());

        // thenApply 测试
        CompletableFuture thenApplyFuture = CompletableFuture.supplyAsync(() -> {
            int i = 3;
            System.out.println("thenApply 正常执行结果 i ：" + i);
            int e = 1 / 0;
            return i;
        }).thenApply(r -> {
            r += 2;
            System.out.println("thenApply 执行结果 ： " + r);
            return r;
        }).exceptionally(e -> {
            System.out.println("thenApply 进入异常执行");
            return 0;
        });
        System.out.println("thenApply 测试结果 : " + thenApplyFuture.get());

        /**
         * 这里延伸两个方法  thenAccept 和 thenRun。其实 和上面两个方法差不多，都是等待前面一个任务执行完 再执行。
         * 区别就在于thenAccept接收前面任务的结果，且无需return。而thenRun只要前面的任务执行完成，它就执行，不关心前面的执行结果如何
         *
         * 如果前面的任务抛了异常，非正常结束，这两个方法是不会执行的，所以处理不了异常情况。
         */

        // 合并操作方法  thenCombine 和 thenAcceptBoth
        thenCombineTest();
        thenAcceptBothTest();

    }

    /**
     * thenCombine 测试
     * @throws Exception
     */
    public static void thenCombineTest() throws Exception {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("进入 thenCombine future1 。。。");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            return 2;
        });

        CompletableFuture result = future1.thenCombine(future2, Integer::sum);

        //这里的get是阻塞的，需要等上面两个任务都完成
        System.out.println("thenCombine 测试结果 ：" + result.get());
    }

    /**
     * thenAcceptBoth 测试
     * @throws Exception
     */
    public static void thenAcceptBothTest() throws Exception {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("进入 thenAcceptBoth future1 。。。");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 2);

        future1.thenAcceptBothAsync(future2, (r1, r2) -> {
            System.out.println("r1 + r2 结果 ： " + (r1 + r2));
        });

        //thenAcceptBothAsync 不会阻塞
        System.out.println("thenAcceptBothTest 继续执行");

        allOfTest();
    }

    /**
     * allOf
     *
     *  它接收一个可变入参，既可以接收CompletableFuture单个对象，可以接收其数组对象。
     */
    public static void allOfTest() {
        Long startTime = System.currentTimeMillis();

        //结果集
        List<String> list = new ArrayList<>();

        List<Integer> taskList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        CompletableFuture[] futures = taskList.stream()
                .map(integer -> CompletableFuture.supplyAsync(() -> sleep(integer))
                        .thenApply(integer1 -> Integer.toString(integer1))
                        .whenComplete((s, e) -> {
                            System.out.println("任务" + s + "完成, 异常： " + e + "时间 :" + new Date());
                            list.add(s);
                        })
                ).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        System.out.println("list = " + list + " 耗时 ：" + (System.currentTimeMillis() - startTime) / 1000);
    }

    public static Integer sleep(Integer i) {
        try {
            if (i == 1) {
                Thread.sleep(1000L);
            } else if (i == 2) {
                Thread.sleep(2000L);
            } else if (i == 3) {
                Thread.sleep(5000L);
            } else {
                Thread.sleep(500L);
            }
        } catch (InterruptedException e) {

        }
        return i;
    }
}
