package framework.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jun.
 */
public abstract class Maps {

    public static <K, V> Map<K, V> concurrentHashMap(int initialCapacity) {
        return new ConcurrentHashMap<>(initialCapacity);
    }

    public static <K, V> Map<K, V> hashMap(int initialCapacity) {
        return new HashMap(initialCapacity);
    }
}
