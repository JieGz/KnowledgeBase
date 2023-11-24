package com.knowledge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author jieguangzhi
 * @date 2023-11-24
 */
public class Other {
    private static final Logger log = LogManager.getLogger(Other.class);

    public static void printLog() {
        log.debug("Other debug!");
        log.info("Other info!");
        log.warn("Other warn!");
        log.error("Other error!");
        log.fatal("Other fatal!");
    }
}
