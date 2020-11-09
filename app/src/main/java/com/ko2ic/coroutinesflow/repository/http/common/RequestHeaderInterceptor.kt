package com.ko2ic.coroutinesflow.repository.http.common

import okhttp3.Interceptor
import okhttp3.Response

class RequestHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("headerKey", "value")
        return chain.proceed(builder.build())
    }
}