package com.knowledge.base;

/**
 * @author jieguangzhi
 * @date 2022-08-27
 */
public class KnowledgeOfRxJava {

    public static void main(String[] args) {
        Integer num = null;
        try {
            test(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void test(int a) {
        if (a == 1) {
            System.out.println(1);
        } else if ( a== 2){
            System.out.println(2);
        }
        System.out.println("6666");
    }
}
