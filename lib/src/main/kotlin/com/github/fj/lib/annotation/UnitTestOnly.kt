/*
 * FJ's Kotlin utilities
 *
 * Distributed under no licences and no warranty.
 * Use this software at your own risk.
 */
package com.github.fj.lib.annotation

/**
 * Annotates a type is designed only for unit tests.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 05 - Jul - 2018
 */
@Target(AnnotationTarget.FILE, AnnotationTarget.CLASS)
annotation class UnitTestOnly
