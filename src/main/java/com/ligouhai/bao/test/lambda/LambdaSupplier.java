package com.ligouhai.bao.test.lambda;

import java.util.function.Supplier;

/**
 * @author ligouhai
 * @date 2020-03-17 16:34
 * @description supplier 测试
 */
public class LambdaSupplier {

    private static String supplierString(Supplier<String> supplier) {
        return supplier.get();
    }

    public static void main(String[] args) {
        System.out.println(supplierString(() -> "打印数据"));

        System.out.println((new Supplier<String>() {

            @Override
            public String get() {
                return "打印数据2";
            }
        }).get());

        System.out.println(((Supplier<String>) () -> "打印数据3").get());
    }
}
