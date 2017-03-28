package framework.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jun.
 */
public abstract class Maps {

    public static ConcurrentHashMap concurrentHashMap(int initialCapacity) {
        return new ConcurrentHashMap<>(initialCapacity);
    }
}
