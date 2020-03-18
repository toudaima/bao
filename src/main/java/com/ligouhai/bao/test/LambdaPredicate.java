package com.ligouhai.bao.test;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * @author ligouhai
 * @date 2020-03-18 11:24
 * @description Predicate
 */
public class LambdaPredicate {

    /**
     *  判断字符串是否存在o  即使生产者 又是消费者接口
      */
    private static void predicateTest1(Predicate<String> predicate) {
        boolean b = predicate.test("oo 1");
        System.out.println(b);
    }

    /**
     *  判断是否同时存在 o 1
     * @param predicate1
     * @param predicate2
     *  使用 and
     */
    private static void predicateAnd(Predicate<String> predicate1, Predicate<String> predicate2) {
        boolean b = predicate1.and(predicate2).test("oo 1");
        System.out.println(b);
    }

    /**
     * or 或 满足一个则返回true
     * @param predicate1
     * @param predicate2
     */
    private static void predicateOr(Predicate<String> predicate1, Predicate<String> predicate2) {
        boolean b = predicate1.or(predicate2).test("oo 1");
        System.out.println(b);
    }

    /**
     * 取反结果， 如果存在  则返回false
     * @param predicate
     *  使用 negate
     */
    private static void predicateNegate(Predicate<String> predicate) {
        boolean b = predicate.negate().test("oo 1");
        System.out.println(b);
    }

    private static void getFemaleAndname(Predicate<String> one,
                                         Predicate<String> two, String[] arr) {
        for (String string : arr) {
            if (one.and(two).test(string)) {
                System.out.println(string);
            }
        }
    }

    public static void main(String[] args) {

        predicateTest1((s) -> s.contains("o"));

        predicateAnd((s1) -> s1.contains("o"), (s2) -> s2.contains("2"));

        predicateOr((s1) -> s1.contains("w"), (s2) -> s2.contains("e"));

        predicateNegate((s) -> s.contains("w"));

        //筛选名字为4个字 性别为女的人
        String[] array = { "迪丽热巴,女", "古力娜扎,女", "马尔扎哈,男", "赵丽颖,女" };
        getFemaleAndname((s) -> s.split(",")[0].length() == 4,
                (s) -> s.split(",")[1].equals("女"), array);

    }
}
