/*
 * FJ's Kotlin utilities
 *
 * Distributed under no licences and no warranty.
 * Use this software at your own risk.
 */
package com.github.fj.lib.util

import com.github.fj.lib.annotation.VisibleForTesting
import java.lang.ref.SoftReference
import java.util.concurrent.*
import kotlin.collections.MutableMap.MutableEntry

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 23 - Nov - 2018
 */
internal class FastCollectedLruCacheImpl<K, V>(maxCapacity: Int) : FastCollectedLruCache<K, V> {
    private val hardCacheSize = Math.round(maxCapacity * 0.75f)
    private val softCacheSize = maxCapacity - hardCacheSize

    @VisibleForTesting
    internal val hardCache = object : LinkedHashMap<K, V>(hardCacheSize, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableEntry<K, V>): Boolean {
            if (size > hardCacheSize) {
                softCache[eldest.key] = SoftReference(eldest.value)
                return true
            }

            return false
        }
    }
    @VisibleForTesting
    internal val softCache = ConcurrentHashMap<K, SoftReference<V>>(softCacheSize)

    override fun put(key: K, value: V): V? {
        softCache.remove(key)
        synchronized(hardCache) {
            return hardCache.put(key, value)
        }
    }

    override fun get(key: K): V? {
        synchronized(hardCache) {
            hardCache[key]?.takeIf { it != null }?.let { v ->
                // Move element to first position of hard cache
                hardCache.remove(key)
                println(hardCache)
                hardCache[key] = v
                println(hardCache)
                return v
            }
        }

        var maybeValue: V? = null
        softCache[key]?.takeIf { it.get() != null }?.get()?.let { v ->
            synchronized(hardCache) {
                // Move element to first position of hard cache
                hardCache.remove(key)
                hardCache[key] = v
            }
            maybeValue = v
        }

        softCache.remove(key)
        return maybeValue
    }

    override fun remove(key: K): V? {
        softCache.remove(key)
        synchronized(hardCache) {
            return hardCache.remove(key)
        }
    }

    override fun clear() {
        synchronized(hardCache) {
            hardCache.clear()
        }
        softCache.clear()
    }
}
