package com.knowledge.base.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author jieguangzhi
 * @date 2024-01-30
 */
@Component
public class I18nUtils {

    @Autowired
    private LocaleMessage localeMessage;

    /**
     * 获取key
     *
     * @param key
     * @return
     */
    public String getKey(String key) {
        String name = localeMessage.getMessage(key);
        return name;
    }

    /**
     * 获取指定哪个配置文件下的key
     *
     * @param key
     * @param local
     * @return
     */
    public String getKey(String key, Locale local) {
        String name = localeMessage.getMessage(key, local);
        return name;
    }
}
