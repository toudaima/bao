package com.ligouhai.bao.test;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author ligouhai
 * @date 2020-03-18 09:31
 * @description consumer 测试
 */
public class LambdaConsumer {

    public static void consumerTest(Consumer<String> consumer) {
        consumer.accept("what's wrong");
    }



    public static void formatPerson(Consumer<String[]> c1, Consumer<String[]> c2) {
        c1.andThen(c2).accept(new String[] {"李逍遥,男", "亚托克斯,男", "妲己,女"});
    }

    private static void printInfo(Consumer<String> c1, Consumer<String> c2, String[] array) {
        for (String info : array) {
            c1.andThen(c2).accept(info);
        }
    }

    public static void main(String[] args) {
        consumerTest(s -> System.out.println(s));

        formatPerson((s1) -> {
            for (int i = 0; i < s1.length; i ++) {
                System.out.print(s1[i].split(",")[0] + "");
            }
        }, (s2) -> {
            System.out.println();
            for (int i = 0; i < s2.length; i ++) {
                System.out.print(s2[i].split(",")[1] + "");
            }
        });
        System.out.println();
        String[] datas = {"李逍遥,男", "亚托克斯,男", "妲己,女"};
        printInfo(s -> System.out.print(s.split(",")[0] + " "), s2 -> System.out.print(s2.split(",")[1] + " "), datas);
    }
}
