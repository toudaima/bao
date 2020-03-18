package com.ligouhai.bao.test.lambda;

import com.ligouhai.bao.service.MyFunctionalService;

/**
 * @author ligouhai
 * @date 2020-03-17 15:34
 * @description FunctionalInterface注解
 */
public class LambdaFunctional {

    public static void print(MyFunctionalService myFunctionalService) {
        myFunctionalService.print();
    }

    public static void main(String[] args) {
        print(() -> System.out.println("123456"));
    }
}
