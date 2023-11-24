package com.knowledge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author jieguangzhi
 * @date 2023-11-24
 */
public class Another {
    private static final Logger log = LogManager.getLogger(Another.class);

    public static void printLog() {
        log.debug("Another debug!");
        log.info("Another info!");
        log.warn("Another warn!");
        log.error("Another error!");
        log.fatal("Another fatal!");
    }
}
