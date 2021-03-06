/*
 * FJ's Kotlin utilities
 *
 * Distributed under no licences and no warranty.
 * Use this software at your own risk.
 */
package com.github.fj.lib.net

import java.net.URI

/**
 * Splits path segments of given URI into a list of string.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 09 - Mar - 2018
 */
fun URI.pathSegments(): List<String> {
    var path = this.path
    when {
        path.isNullOrEmpty() -> return ArrayList()
        path == "/"          -> return ArrayList<String>().apply {
            add("/")
        }
    }

    if (path.startsWith("/")) {
        path = path.substring(1)
    }

    return path.split("/")
}
