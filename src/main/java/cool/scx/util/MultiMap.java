package cool.scx.util;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * a
 *
 * @param <K> a
 * @param <V> a
 */
public final class MultiMap<K, V> {

    /**
     * 真正存储数据的 map
     */
    private final Map<K, List<V>> map = new LinkedHashMap<>();

    /**
     * 总数
     */
    private int size = 0;

    /**
     * size
     *
     * @return a
     */
    public int size() {
        return size;
    }

    /**
     * a
     *
     * @return a
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * a
     *
     * @param key a
     * @return a
     */
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    /**
     * a
     *
     * @param value a
     * @return a
     */
    public boolean containsValue(V value) {
        return map.values().stream().anyMatch(collection -> collection.contains(value));
    }

    /**
     * a
     *
     * @param key a
     * @return a
     */
    public List<V> get(K key) {
        return map.computeIfAbsent(key, k -> new ArrayList<>());
    }

    /**
     * a
     *
     * @param key   a
     * @param value a
     * @return a
     */
    public boolean put(K key, V value) {
        var collection = get(key);
        size = size + 1;
        return collection.add(value);
    }

    /**
     * a
     *
     * @param key    a
     * @param values a
     * @return a
     */
    public boolean putAll(K key, List<? extends V> values) {
        size = size + values.size();
        return !values.isEmpty() && get(key).addAll(values);
    }

    /**
     * a
     *
     * @param key   a
     * @param value a
     * @return a
     */
    public boolean remove(K key, V value) {
        var collection = map.get(key);
        if (collection != null && collection.remove(value)) {
            size = size - 1;
            return true;
        }
        return false;
    }

    /**
     * 根据 key 移除所有
     *
     * @param key key
     * @return key
     */
    public List<V> removeAll(K key) {
        var collection = map.remove(key);
        if (collection == null) {
            return Collections.emptyList();
        }
        var output = new ArrayList<>(collection);
        size = size - collection.size();
        collection.clear();
        return Collections.unmodifiableList(output);
    }

    /**
     * 清楚所有 (通过 get 获取的也会被清空)
     */
    public void clear() {
        for (var collection : map.values()) {
            collection.clear();
        }
        map.clear();
        size = 0;
    }

    /**
     * a
     *
     * @return a
     */
    public Set<K> keySet() {
        return map.keySet();
    }

    /**
     * a
     *
     * @return a
     */
    public List<V> values() {
        return map.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * 转为 map
     *
     * @return a
     */
    public Map<K, List<V>> asMap() {
        return map;
    }

    /**
     * 循环
     *
     * @param action a
     */
    public void forEach(BiConsumer<? super K, ? super V> action) {
        map.forEach((key, valueCollection) -> valueCollection.forEach(value -> action.accept(key, value)));
    }

}
