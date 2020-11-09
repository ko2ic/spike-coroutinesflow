package com.ko2ic.coroutinesflow.common.repository.http

interface HttpClientLocator {

    fun <T> lookup(clazz: Class<T>): T
}