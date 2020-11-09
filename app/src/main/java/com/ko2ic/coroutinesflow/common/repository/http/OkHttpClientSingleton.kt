package com.ko2ic.coroutinesflow.common.repository.http

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.internal.threadFactory
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class OkHttpClientSingleton private constructor() {

    val httpClient: OkHttpClient

    init {
        val logging = HttpLoggingInterceptor()
        // TODO: 環境によって変える
        logging.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addNetworkInterceptor(logging)
        builder.addInterceptor(logging)
        builder.dispatcher(
            Dispatcher(
                ThreadPoolExecutor(
                    0, 128, 60, TimeUnit.SECONDS,
                    SynchronousQueue<Runnable>(), threadFactory("OkHttp Dispatcher", false)
                )
            )
        )
        this.httpClient = builder.build()
    }

    private object Holder {
        val INSTANCE = OkHttpClientSingleton()
    }

    companion object {
        val instance: OkHttpClientSingleton by lazy { Holder.INSTANCE }
    }
}