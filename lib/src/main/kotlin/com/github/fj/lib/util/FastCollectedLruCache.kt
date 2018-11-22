/*
 * FJ's Kotlin utilities
 *
 * Distributed under no licences and no warranty.
 * Use this software at your own risk.
 */
package com.github.fj.lib.util

/**
 * A simple LRU cache implementation. This cache maybe useful for memory consuming objects.
 * Based on initial cache size, stored items which access rate drops lower than 25% will be
 * aggressively cleared from memory(It depends on GC settings - its implementation relies on
 * [java.lang.ref.SoftReference].).
 *
 * Due to its GC friendly feature, many operations that depends on size such as  `size()`,
 * `entrySet()`, etc., could derive inconsistent results, therefore no such map operations are
 * offered.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 19 - Nov - 2018
 */
interface FastCollectedLruCache<K, V> {
    /**
     * @return the previous value associated with key, or `null` if there was no association.
     */
    fun put(key: K, value: V): V?

    fun get(key: K): V?

    fun remove(key: K): V?

    fun clear()

    companion object {
        fun <K, V> create(maxCapacity: Int): FastCollectedLruCache<K, V> =
                FastCollectedLruCacheImpl(maxCapacity)
    }
}
