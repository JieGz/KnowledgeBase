package com.shopline.jdp.options;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.BiFunction;

import static com.shopline.jdp.options.OptionsUtils.canBePrefixMap;
import static com.shopline.jdp.options.OptionsUtils.containsPrefixMap;
import static com.shopline.jdp.options.OptionsUtils.convertToPropertiesPrefixKey;
import static com.shopline.jdp.options.OptionsUtils.convertToPropertiesPrefixed;
import static com.shopline.jdp.options.OptionsUtils.removePrefixMap;

/**
 * @author jieguangzhi
 * @date 2024-03-29
 */
public class Options implements Serializable {
    private static final long serialVersionUID = 1L;

    private final HashMap<String, Object> data;

    public Options() {
        this.data = new HashMap<>();
    }

    public Options(Map<String, Object> map) {
        this();
        map.forEach(this::set);
    }

    public static Options fromMap(Map<String, Object> map) {
        return new Options(map);
    }

    public synchronized void setString(String key, String value) {
        data.put(key, value);
    }

    public synchronized void set(String key, Object value) {
        data.put(key, value);
    }

    public synchronized <T> Options set(ConfigOption<T> option, T value) {
        final boolean canBePrefixMap = canBePrefixMap(option);
        setValueInternal(option.key(), value, canBePrefixMap);
        return this;
    }

    public synchronized <T> T get(ConfigOption<T> option) {
        return getOptional(option).orElseGet(option::defaultValue);
    }

    public synchronized String getString(String key) {
        return String.valueOf(data.get(key));
    }

    public synchronized <T> Optional<T> getOptional(ConfigOption<T> option) {
        Optional<Object> rawValue = getRawValueFromOption(option);
        Class<?> clazz = option.getClazz();

        try {
            return rawValue.map(v -> OptionsUtils.convertValue(v, clazz));
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    String.format(
                            "Could not parse value '%s' for key '%s'.",
                            rawValue.map(Object::toString).orElse(""), option.key()),
                    e);
        }
    }

    public synchronized boolean contains(ConfigOption<?> configOption) {
        synchronized (this.data) {
            final BiFunction<String, Boolean, Optional<Boolean>> applier =
                    (key, canBePrefixMap) -> {
                        if (canBePrefixMap && containsPrefixMap(this.data, key)
                                || this.data.containsKey(key)) {
                            return Optional.of(true);
                        }
                        return Optional.empty();
                    };
            return applyWithOption(configOption, applier).orElse(false);
        }
    }

    public synchronized Set<String> keySet() {
        return data.keySet();
    }

    public synchronized Map<String, Object> toMap() {
        return data;
    }

    public synchronized Options removePrefix(String prefix) {
        return new Options(convertToPropertiesPrefixKey(data, prefix));
    }

    public synchronized void remove(String key) {
        data.remove(key);
    }

    public synchronized boolean containsKey(String key) {
        return data.containsKey(key);
    }

    /** Adds all entries in these options to the given {@link Properties}. */
    public synchronized void addAllToProperties(Properties props) {
        props.putAll(this.data);
    }

    public synchronized String getString(ConfigOption<String> option) {
        return get(option);
    }

    public synchronized boolean getBoolean(String key, boolean defaultValue) {
        return getRawValue(key).map(OptionsUtils::convertToBoolean).orElse(defaultValue);
    }

    public synchronized int getInteger(String key, int defaultValue) {
        return getRawValue(key).map(OptionsUtils::convertToInt).orElse(defaultValue);
    }

    public synchronized String getString(String key, String defaultValue) {
        return getRawValue(key).map(OptionsUtils::convertToString).orElse(defaultValue);
    }

    public synchronized void setInteger(String key, int value) {
        setValueInternal(key, value);
    }

    public synchronized long getLong(String key, long defaultValue) {
        return getRawValue(key).map(OptionsUtils::convertToLong).orElse(defaultValue);
    }


    private <T> void setValueInternal(String key, T value, boolean canBePrefixMap) {
        if (key == null) {
            throw new NullPointerException("Key must not be null.");
        }
        if (value == null) {
            throw new NullPointerException("Value must not be null.");
        }

        synchronized (this.data) {
            if (canBePrefixMap) {
                removePrefixMap(this.data, key);
            }
            this.data.put(key, OptionsUtils.convertToString(value));
        }
    }

    private Optional<Object> getRawValueFromOption(ConfigOption<?> configOption) {
        return applyWithOption(configOption, this::getRawValue);
    }

    private Optional<Object> getRawValue(String key) {
        return getRawValue(key, false);
    }

    private Optional<Object> getRawValue(String key, boolean canBePrefixMap) {
        if (key == null) {
            throw new NullPointerException("Key must not be null.");
        }

        synchronized (this.data) {
            final Object valueFromExactKey = this.data.get(key);
            if (!canBePrefixMap || valueFromExactKey != null) {
                return Optional.ofNullable(valueFromExactKey);
            }
            final Map<String, Object> valueFromPrefixMap = convertToPropertiesPrefixed(data, key);
            if (valueFromPrefixMap.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(valueFromPrefixMap);
        }
    }

    private <T> Optional<T> applyWithOption(
            ConfigOption<?> option, BiFunction<String, Boolean, Optional<T>> applier) {
        final boolean canBePrefixMap = canBePrefixMap(option);
        final Optional<T> valueFromExactKey = applier.apply(option.key(), canBePrefixMap);
        if (valueFromExactKey.isPresent()) {
            return valueFromExactKey;
        } else if (option.hasFallbackKeys()) {
            // try the fallback keys
            for (FallbackKey fallbackKey : option.fallbackKeys()) {
                final Optional<T> valueFromFallbackKey =
                        applier.apply(fallbackKey.getKey(), canBePrefixMap);
                if (valueFromFallbackKey.isPresent()) {
                    return valueFromFallbackKey;
                }
            }
        }
        return Optional.empty();
    }

    private <T> void setValueInternal(String key, T value) {
        setValueInternal(key, value, false);
    }

    @Override
    public synchronized boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Options options = (Options) o;
        return Objects.equals(data, options.data);
    }

    @Override
    public synchronized int hashCode() {
        return Objects.hash(data);
    }
}
