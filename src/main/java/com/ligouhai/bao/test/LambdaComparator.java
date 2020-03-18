package com.ligouhai.bao.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author ligouhai
 * @date 2020-03-17 16:00
 * @description 使用Lambda作为参数和返回值
 */
public class LambdaComparator {

    /**
     * 以下三种写法表达的意思都是一样的
     *
     */

    private static Comparator<String> lambdaComparator1() {
        return (o1, o2) -> o1.length() - o2.length();
    }

    private static Comparator<String> lambdaComparator2() {
        return Comparator.comparingInt(String::length);
    }

    public static Comparator<String> comparator() {
        return new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        };
    }

    public static void main(String[] args) {
        String[] array = {"ab", "cds", "s", "eerw"};
        System.out.println(Arrays.toString(array));
        //普通方法
//        Arrays.sort(array, comparator());
        //lambda1
        Arrays.sort(array, ((o1, o2) -> o1.length() - o2.length()));
        //lambda2
//        Arrays.sort(array, (Comparator.comparingInt(String::length)));
        System.out.println(Arrays.toString(array));
    }
}
