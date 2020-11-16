package com.ko2ic.coroutinesflow.common.repository.http

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import okhttp3.OkHttpClient

abstract class KtorHttpClientBase {
    val client by lazy {
        val httpClient = OkHttpClientSingleton.instance.httpClient
        val builder = this.addInterceptor(httpClient.newBuilder())
        HttpClient(OkHttp) {
            engine {
//                // https://square.github.io/okhttp/3.x/okhttp/okhttp3/OkHttpClient.Builder.html
//                config { // this: OkHttpClient.Builder ->
//                    // ...
//                    followRedirects(true)
//                    // ...
//                }
//
//                // https://square.github.io/okhttp/3.x/okhttp/okhttp3/Interceptor.html
//                addInterceptor(interceptor)
//                addNetworkInterceptor(interceptor)

                // 自分でインスタンスしたものを使う場合、指定しなければ自動でインスタンスされる
                preconfigured = builder.build()
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }

        }
    }

    abstract val baseUrl: String

    // 継承先で処理を入れたい時にoverrideする
    open fun addInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder = builder

    suspend inline fun <reified T> get(path: String, parameters: Map<String, Any> = emptyMap()): T {
        val url = URLBuilder(baseUrl + path)
        parameters.entries.forEach {
            url.parameters.append(it.key, it.value.toString())
        }
        // HeaderはaddInterceptorに入れる
        return this.client.get(url.buildString())
    }

    suspend inline fun <reified T> post(path: String, any: Any): T {
        val requestPath = URLBuilder(baseUrl + path).buildString()
        // HeaderはaddInterceptorに入れる
        return client.post<T> {
            url(requestPath)
            body = any
        }

    }

    suspend inline fun <reified T> put(path: String, any: Any): T {
        val requestPath = URLBuilder(baseUrl + path).buildString()
        // HeaderはaddInterceptorに入れる
        return client.put<T> {
            url(requestPath)
            body = any
        }
    }

    suspend inline fun <reified T> delete(path: String, any: Any): T {
        val requestPath = URLBuilder(baseUrl + path).buildString()
        // HeaderはaddInterceptorに入れる
        return client.delete<T> {
            url(requestPath)
            body = any
        }
    }
}