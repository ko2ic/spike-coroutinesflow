package com.ko2ic.coroutinesflow.repository.http.common

import com.ko2ic.coroutinesflow.common.repository.http.KtorHttpClientBase
import okhttp3.OkHttpClient
import javax.inject.Inject

open class KtorHttpClientDefault @Inject constructor() : KtorHttpClientBase() {

    override val baseUrl: String = "https://jsonplaceholder.typicode.com"

    override fun addInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.addInterceptor(RequestHeaderInterceptor())
        return builder
    }
}

open class KtorHttpClientErrorMock @Inject constructor() : KtorHttpClientBase() {

    override val baseUrl: String = "https://api.github.com"

    override fun addInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.addInterceptor(RequestHeaderInterceptor())
        return builder
    }
}