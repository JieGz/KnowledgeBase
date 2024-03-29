package com.shopline.jdp.options;

import com.shopline.jdp.options.description.Description;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static com.shopline.jdp.utils.Preconditions.checkNotNull;

/**
 * @author jieguangzhi
 * @date 2024-03-29
 */
public class ConfigOption<T> {
    private static final FallbackKey[] EMPTY = new FallbackKey[0];

    static final Description EMPTY_DESCRIPTION = Description.builder().text("").build();

    private final String key;

    private final FallbackKey[] fallbackKeys;

    private final T defaultValue;

    private final Description description;

    private final Class<?> clazz;

    ConfigOption(
            String key,
            Class<?> clazz,
            Description description,
            T defaultValue,
            FallbackKey... fallbackKeys) {
        this.key = checkNotNull(key);
        this.description = description;
        this.defaultValue = defaultValue;
        this.fallbackKeys = fallbackKeys == null || fallbackKeys.length == 0 ? EMPTY : fallbackKeys;
        this.clazz = checkNotNull(clazz);
    }

    public ConfigOption<T> withFallbackKeys(String... fallbackKeys) {
        final Stream<FallbackKey> newFallbackKeys = Arrays.stream(fallbackKeys).map(FallbackKey::createFallbackKey);
        final Stream<FallbackKey> currentAlternativeKeys = Arrays.stream(this.fallbackKeys);

        // put fallback keys first so that they are prioritized
        final FallbackKey[] mergedAlternativeKeys =
                Stream.concat(newFallbackKeys, currentAlternativeKeys).toArray(FallbackKey[]::new);
        return new ConfigOption<>(key, clazz, description, defaultValue, mergedAlternativeKeys);
    }

    public ConfigOption<T> withDeprecatedKeys(String... deprecatedKeys) {
        final Stream<FallbackKey> newDeprecatedKeys = Arrays.stream(deprecatedKeys).map(FallbackKey::createDeprecatedFallbackKey);
        final Stream<FallbackKey> currentAlternativeKeys = Arrays.stream(this.fallbackKeys);

        // put deprecated keys last so that they are de-prioritized
        final FallbackKey[] mergedAlternativeKeys =
                Stream.concat(currentAlternativeKeys, newDeprecatedKeys)
                        .toArray(FallbackKey[]::new);
        return new ConfigOption<>(key, clazz, description, defaultValue, mergedAlternativeKeys);
    }

    public ConfigOption<T> withDescription(final String description) {
        return withDescription(Description.builder().text(description).build());
    }

    public ConfigOption<T> withDescription(final Description description) {
        return new ConfigOption<>(key, clazz, description, defaultValue, fallbackKeys);
    }


    Class<?> getClazz() {
        return clazz;
    }

    public String key() {
        return key;
    }

    public boolean hasDefaultValue() {
        return defaultValue != null;
    }

    public T defaultValue() {
        return defaultValue;
    }

    public boolean hasFallbackKeys() {
        return fallbackKeys != EMPTY;
    }

    public Iterable<FallbackKey> fallbackKeys() {
        return (fallbackKeys == EMPTY) ? Collections.emptyList() : Arrays.asList(fallbackKeys);
    }

    public Description description() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && o.getClass() == ConfigOption.class) {
            ConfigOption<?> that = (ConfigOption<?>) o;
            return this.key.equals(that.key)
                    && Arrays.equals(this.fallbackKeys, that.fallbackKeys)
                    && (this.defaultValue == null
                    ? that.defaultValue == null
                    : (that.defaultValue != null
                    && this.defaultValue.equals(that.defaultValue)));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 31 * key.hashCode()
                + 17 * Arrays.hashCode(fallbackKeys)
                + (defaultValue != null ? defaultValue.hashCode() : 0);
    }

    @Override
    public String toString() {
        return String.format(
                "Key: '%s' , default: %s (fallback keys: %s)",
                key, defaultValue, Arrays.toString(fallbackKeys));
    }
}
