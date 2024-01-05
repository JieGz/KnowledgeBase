package org.knowledge;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author jieguangzhi
 * @date 2023-08-24
 */
public class Main {
    public static void main(String[] args) {
        final Config config = ConfigFactory.load("application.conf");

        final String format = String.format("GET-/a/b/c/%s", 10086);
        System.out.println(format);
    }
}