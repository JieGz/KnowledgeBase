package com.shopline.jdp;

import java.util.HashMap;

/**
 * @author jieguangzhi
 * @date 2024-03-28
 */
public class Main {
    public static void main(String[] args) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("k2", "v1");
        map.put("b2", true);
        map.put("n1", 10.3);
        final CoreOptions coreOptions = new CoreOptions(map);
        System.out.println(coreOptions.get(CoreOptions.TEST));
        System.out.println(coreOptions.get(CoreOptions.USE));
        System.out.println(coreOptions.get(CoreOptions.NUMBER));
    }
}