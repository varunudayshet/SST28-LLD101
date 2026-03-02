package com.example.metrics;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Proper lazy, thread-safe Singleton using Static Holder pattern.
 */
public final class MetricsRegistry implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // Used to block reflection
    private static boolean instanceCreated = false;

    private final Map<String, Long> counters = new HashMap<>();

    /**
     * Private constructor to prevent external instantiation.
     * Also blocks reflection-based creation.
     */
    private MetricsRegistry() {
        if (instanceCreated) {
            throw new IllegalStateException("Singleton already initialized");
        }
        instanceCreated = true;
    }

    /**
     * Static holder class —> loaded only when referenced.
     * JVM guarantees thread-safe initialization.
     */
    private static class Holder {
        private static final MetricsRegistry INSTANCE = new MetricsRegistry();
    }

    /**
     * Lazy and Thread-safe access point.
     */
    public static MetricsRegistry getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void setCount(String key, long value) {
        counters.put(key, value);
    }

    public synchronized void increment(String key) {
        counters.put(key, getCount(key) + 1);
    }

    public synchronized long getCount(String key) {
        return counters.getOrDefault(key, 0L);
    }

    public synchronized Map<String, Long> getAll() {
        return Collections.unmodifiableMap(new HashMap<>(counters));
    }

    /**
     * Ensures deserialization does NOT create a new instance.
     */
    @Serial
    private Object readResolve() {
        return getInstance();
    }
}