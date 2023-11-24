package com.knowledge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author jieguangzhi
 * @date 2023-11-24
 */
public class AsyncLogOperator {

    private static final Logger log = LogManager.getLogger(AsyncLogOperator.class);

    public static void printLog() {
        for (int i = 0; i < 100; i++) {
            log.debug("AsyncLogOperator debug!");
            log.info("AsyncLogOperator info!");
            log.warn("AsyncLogOperator warn!");
            log.error("AsyncLogOperator error!");
            log.fatal("AsyncLogOperator fatal!");
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("异步logger后的逻辑");
        }
    }
}
