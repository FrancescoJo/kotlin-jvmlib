/*
 * FJ's Kotlin utilities
 *
 * Distributed under no licences and no warranty.
 * Use this software at your own risk.
 */
package com.github.fj.lib.util

/**
 * This type holder represents implementing object may be *EMPTY*.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 26 - Jun - 2018
 */
interface EmptyObject<T> {
    @Suppress("PropertyName")
    val EMPTY: T
}
