package com.knowledge.base;

/**
 * @author jieguangzhi
 * @date 2022-08-27
 */
public class KnowledgeOfRxJava {

    public static void main(String[] args) {


        final Thread thread = new Thread(() -> {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("shutdown!");
            }));
        });

        thread.setDaemon(false);
        thread.start();
    }
}
