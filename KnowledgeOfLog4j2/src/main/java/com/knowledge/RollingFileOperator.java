package com.knowledge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author jieguangzhi
 * @date 2023-11-24
 */
public class RollingFileOperator {
    private static final Logger log = LogManager.getLogger(RollingFileOperator.class);

    public static void printLog() {
        for (int i = 0; i < 100000; i++) {
            log.debug("RollingFileOperator debug!");
            log.info("RollingFileOperator info!");
            log.warn("RollingFileOperator warn!");
            log.error("RollingFileOperator error!");
            log.fatal("RollingFileOperator fatal!");
        }
    }
}
