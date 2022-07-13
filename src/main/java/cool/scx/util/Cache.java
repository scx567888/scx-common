package cool.scx.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Cache class.</p>
 *
 * @param <K> a
 * @param <V> a
 * @author scx567888
 * @version 0.0.1
 */
public final class Cache<K, V> extends LinkedHashMap<K, V> {

    /**
     * a
     */
    private final int maxCount;

    /**
     * <p>Constructor for Cache.</p>
     *
     * @param maxCount a int
     */
    public Cache(int maxCount) {
        this.maxCount = maxCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxCount;
    }

}
