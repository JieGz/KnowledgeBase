package org.knowledge;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigList;

import java.util.List;

/**
 * @author jieguangzhi
 * @date 2023-08-24
 */
public class Main {
    public static void main(String[] args) {
        final Config config = ConfigFactory.load("application.conf");


    }
}