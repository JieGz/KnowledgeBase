package com.knowledge;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author jieguangzhi
 * @date 2023-11-24
 */
public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println("Hello world1111!");
        log.debug("Hello world! debug");
        log.info("Hello world! info");
        log.warn("Hello world! warn");
        log.error("Hello world! error");
        log.fatal("Hello world! fatal");
        RollingFileOperator.printLog();
        AsyncLogOperator.printLog();
        Other.printLog();
        Another.printLog();
    }
}