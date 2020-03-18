package com.ligouhai.bao.test;

import com.ligouhai.bao.service.MessageBuilder;

/**
 * @author ligouhai
 * @date 2020-03-17 15:40
 * @description lambda的延迟执行
 */
public class LambdaDelay {

    public static void print(int level, String msg) {
        if (level > 1) {
            System.out.println(msg);
        }
    }

    public static void lambdaPrint(int level, MessageBuilder messageBuilder) {
        if (level > 1) {
            System.out.println(messageBuilder.getMessage());
        }
    }

    public static void main(String[] args) {
        //普通执行
        print(1, "开始打印");
        //lambda方式
        lambdaPrint(1, () -> {
            System.out.println("开始执行");
            return "123456";
        });
    }
}
