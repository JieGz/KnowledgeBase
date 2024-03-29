package com.shopline.jdp;

import com.shopline.jdp.options.ConfigOption;
import com.shopline.jdp.options.ConfigOptions;
import com.shopline.jdp.options.Options;
import com.shopline.jdp.options.description.Description;

import java.util.Map;

/**
 * @author jieguangzhi
 * @date 2024-03-29
 */
public class CoreOptions {

    public static ConfigOption<String> TEST = ConfigOptions.key("k1")
            .stringType()
            .defaultValue("我是一个默认值")
            .withDescription(Description.builder().text("测试用例而已").build());

    public static ConfigOption<Boolean> USE = ConfigOptions.key("b1")
            .booleanType()
            .defaultValue(true);

    public static ConfigOption<Double> NUMBER = ConfigOptions.key("n1")
            .doubleType()
            .defaultValue(1.0);


    private final Options options;

    public CoreOptions(Map<String, Object> config) {
        this.options = Options.fromMap(config);
    }

    public <T> T get(ConfigOption<T> configOption) {
        return options.get(configOption);
    }
}
