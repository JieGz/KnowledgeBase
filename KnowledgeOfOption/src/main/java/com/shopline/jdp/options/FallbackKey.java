package com.shopline.jdp.options;

/**
 * 备选的key，可简单理解成一个别名，当指定的key不存在时，则找备选的key。配置项的名字。
 *
 * @author jieguangzhi
 * @date 2024-03-28
 */
public class FallbackKey {


    static FallbackKey createFallbackKey(String key) {
        return new FallbackKey(key, false);
    }

    static FallbackKey createDeprecatedFallbackKey(String key) {
        return new FallbackKey(key, true);
    }

    private final String key;

    private final boolean isDeprecated;

    private FallbackKey(String key, boolean isDeprecated) {
        this.key = key;
        this.isDeprecated = isDeprecated;
    }

    public String getKey() {
        return key;
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && o.getClass() == FallbackKey.class) {
            FallbackKey that = (FallbackKey) o;
            return this.key.equals(that.getKey()) && (this.isDeprecated == that.isDeprecated());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 31 * key.hashCode() + (isDeprecated ? 1 : 0);
    }

    @Override
    public String toString() {
        return String.format("{key=%s, isDeprecated=%s}", key, isDeprecated);
    }
}
