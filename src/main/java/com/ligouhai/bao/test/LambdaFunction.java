package com.ligouhai.bao.test;

import java.util.function.Function;

/**
 * @author ligouhai
 * @date 2020-03-18 13:44
 * @description Function 测试
 */
public class LambdaFunction {

    // 将数字转换为String类型
    private static void numberToString(Function<Number, String> function) {
        String apply = function.apply(12);
        System.out.println("转换结果:" + apply);
    }
    public static void main(String[] args) {
        numberToString((s)->String.valueOf(s));
    }
}
